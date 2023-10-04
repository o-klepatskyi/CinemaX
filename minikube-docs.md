## Lab3 - K8s and Minikube
### Windows Setup
1. Install docker desktop [link](https://docs.docker.com/desktop/install/windows-install/)
2. Install kubectl [link](https://kubernetes.io/docs/tasks/tools/)
3. Install minikube [link](https://minikube.sigs.k8s.io/docs/start/)
4. Verify installation:
```shell
minikube start
kubectl cluster-info
```
---
### Project Setup (Already done, just verify)
5. Add Dockerfile per service
6. Add k8s config file in any directory (our is located on `/kube/k8s.yaml`)
---
### Deploy and run
1. Start docker desktop
2. Start minikube: 
```
minikube start
minikube tunnel
# don't close this terminal!!!
```
3. Run `./deploy-and-run-minikube.ps`
11. Access the page: `http:\\localhost:8080`
---
## Common Issues
### Connection Reset
Services might take a time to boot, if you see Connection Reset error just wait a little bit
If problem does not resolve itself, restart minikube:
```
minikube stop
minikube start
```