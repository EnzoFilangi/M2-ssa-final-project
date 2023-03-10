FROM node:18-alpine AS build

ENV VITE_API_URL=/ap-backend/api

WORKDIR /app
COPY . .
RUN yarn --frozen-lockfile
RUN yarn build

FROM nginx:1.21-alpine

COPY default.nginx /etc/nginx/conf.d/default.conf

# Remove default nginx website
RUN rm -rf /usr/share/nginx/html/*

COPY --from=build /app/dist /usr/share/nginx/html

RUN touch /var/run/nginx.pid && \
  chown -R nginx:nginx /var/run/nginx.pid && \
  chown -R nginx:nginx /var/cache/nginx