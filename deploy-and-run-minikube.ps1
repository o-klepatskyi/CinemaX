# This script builds, deploys and starts all runnable modules, then stops them on exit
# If you want to exclude any modules, add them to skippedModules below
# Make sure to read throw docs in minikube-docs.md before running this script

$skippedModules = @("commons")

function LOG ($text) {
    Write-Host $text -ForegroundColor Green
}

LOG "Building project..."
./gradlew bootJar
LOG "Project was built successfully"
minikube docker-env | Invoke-Expression

$file = "settings.gradle"

Get-Content -Path $file | ForEach-Object {
    $line = $_

    if ($line -match '^\s*include\s*''(.*)''\s*$') {                # Check if the line starts with 'include'
        $current_module = $matches[1] -replace '^\s*''|''\s*$', ''  # Extract the string after 'include' and trim any leading/trailing spaces and quotes

        if ($current_module -in $skippedModules) {
            LOG "Skipping module: $current_module"
        } else {
            # Perform some action with the string (replace with your action)
            LOG "Creating image: $current_module..."
            docker build -t $current_module ./$current_module
        }
    }
}

LOG "Added all images to minikube"
docker image ls

LOG "Starting services..."
kubectl apply -f kube/k8s.yaml -n cinemax
LOG "Successfully started all services. Verify their statuses below. Press Ctrl-C to close services."
try
{
    kubectl get pods --watch -n cinemax
}
finally
{
    LOG "Deleting services..."
    kubectl delete --all svc -n cinemax
    LOG "Deleting deployments..."
    kubectl delete --all deployment -n cinemax
    LOG "All created services closed. If you see any running pods below, something must be wrong"
    kubectl get pods -n cinemax
    kubectl get service -n cinemax
    pause
}
