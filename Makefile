docker-dev:
	docker compose -f docker-compose.local.yml up -d --build

docker-up:
	docker compose -f docker-compose.local.yml up -d

docker-down:
	docker compose -f docker-compose.local.yml down
