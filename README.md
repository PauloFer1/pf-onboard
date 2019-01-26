# pf-onboard

## Service to onboard Users
Uses Redis to cache the onboarding and sent a final request to User-Service to create the User

- run Redis
docker run --name redisDB -p 6379:6379 -d redis

## Run with minikube
minikube start
minikube dashboard
kubectl create -f k8s-deploy.yml
kubectl create -f k8s-service.yml
kubectl create -f k8s-redis-deploy.yml
kubectl create -f k8s-redis-service.yml
minikube service pf-onboard
minikube service redis-onboard

## Clean Kubernetes
kubectl delete service pf-onboard
kubectl delete deployment pf-onboard
minikube stop
minikube delete


## Useful Kubernetes cli commands
kubectl get deployments
kubectl get services
kubectl get pods
kubectl get events
kubectl config view