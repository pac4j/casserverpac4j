# CAS server for pac4j demos

Running online at Heroku: `https://casserverpac4j.herokuapp.com/login`

Running locally on `http://localhost:8888/cas` via: `mvn clean package; java -Dcas.port=8888 -Dcas.path=/cas -jar target/cas.war`

---

`keytool -genkeypair -alias pac4j-demo -keypass pac4j-demo-passwd -keystore keystore.jks -storepass pac4j-demo-passwd -keyalg RSA -keysize 2048 -validity 3650`
