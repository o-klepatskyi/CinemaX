# Lab3 - K8s
1. Install docker desktop [link](https://docs.docker.com/desktop/install/windows-install/)
2. Install kubectl [link](https://kubernetes.io/docs/tasks/tools/)
3. Install minikube [link](https://minikube.sigs.k8s.io/docs/start/)
4. Verify installation:
```shell
minikube start
kubectl cluster-info
```
5. Add Dockerfile per service
6. Add k8s config file in any directory (our is located on `/kube/k8s.yaml`)
7. Build project: `./gradlew bootJar`
7. Run docker daemon inside minikube cluster: `minikube docker-env | Invoke-Expression`
8. Build and verify services' images
```shell
# for each service
cd film-service
docker build -t film-service .
docker image ls # verify it is uploaded to minikube
```
9. Run pods with service containers:
```
cd /kube
kubectl apply -f k8s.yaml
kubectl get pods --watch # verify pods are running
kubectl logs <pod id> # verify app has started normally
```
10. Run tunnel to be able to connect from outside: `minikube tunnel`. Don't close this terminal!
11. Access the page: `curl http:\\localhost:8080`