FROM node:22-alpine as build
WORKDIR /usr/code
COPY ["./frontend/package.json", "/usr/code/"]
RUN npm ci
EXPOSE 5173

FROM node:22-alpine as run
RUN addgroup allusers && adduser -S -G allusers username
WORKDIR /usr/code
COPY --from=build /usr/code/ .
USER username
CMD ["npm", "run", "start"]