# Camel LDAP Gateway - SOAP Service Communication with Camel Route

## Introduction
This project implements a use case where a SOAP service client communicates with a SOAP service endpoint through a Camel route. The Camel route is responsible for auditing and authorizing access to the endpoint by consulting an LDAP server.

## Utilized Components
- **OpenLDAP**: Lightweight Directory Access Protocol (LDAP) server.
- **Calculator-ws**: SOAP service utilized in the communication.
- **curl**: Command-line tool for transferring data with URLs.
- **Camel**: Integration framework facilitating the routing and mediation rules.

## Steps to Test
Some components are containerized; Podman has been used for this purpose.
1. **First Step**: Execute `ldap/run.sh` to launch OpenLDAP and load the LDIF.
2. **Second Step**: Start the SOAP service by running `soap-service/run.sh`.
3. **Third Step**: Run the Camel route. Navigate to the `camel` directory and execute `mvn quarkus:dev`.
4. **Fourth Step**: Execute the client by running `soap-client/run.sh`.

## Additional Notes
- Ensure that all dependencies are installed and configured properly before executing the steps.
- Make sure to have Podman installed for container management. If not, you can use Docker or another containerization tool instead.
- Adjust permissions and configurations as necessary, especially for network access and container interactions.
- Refer to individual scripts and configuration files for further customization options and troubleshooting guidance.
