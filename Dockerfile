FROM resin/%%RESIN_MACHINE_NAME%%-openjdk
FROM resin/%%RESIN_MACHINE_NAME%%-node:6

ENV DEVICE_TYPE=%%RESIN_MACHINE_NAME%%

RUN apt-get update \
	&& apt-get install -y \
		dnsmasq \
		hostapd \
		iproute2 \
		iw \
		libdbus-1-dev \
		libexpat-dev \
		rfkill \
	&& rm -rf /var/lib/apt/lists/*

RUN mkdir -p /usr/src/app/

WORKDIR /usr/src/app

COPY package.json /usr/src/app/

RUN JOBS=MAX npm install --unsafe-perm --production \
	&& npm cache clean

COPY bower.json .bowerrc /usr/src/app/

RUN ./node_modules/.bin/bower --allow-root install \
	&& ./node_modules/.bin/bower --allow-root cache clean

COPY . /usr/src/app/

RUN ./node_modules/.bin/coffee -c ./src

RUN echo "resin-wifi-conect@<3.0.0 is no longer maintained. Upgrade to resin-wifi-connect@^3.0.0."

CMD bash start

