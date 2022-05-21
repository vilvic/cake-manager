FROM node:18.2.0-alpine3.14

RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app

COPY --chown=node:node . /usr/src/app

RUN npm i
RUN chown node /usr/src/app

USER node

EXPOSE 3000

CMD [ "npm", "start" ]