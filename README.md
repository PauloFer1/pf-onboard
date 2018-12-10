# pf-onboard

## Service to onboard Users
Uses Redis to cache the onboarding and sent a final request to User-Service to create the User

- run Redis
docker run --name redisDB -p 6379:6379 -d redis
