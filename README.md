# Hello World: AWS Lambda and API Gateway in Java

Implementation of the workshop from Escuela Colombiana de Ingeniería Julio
Garavito. The service calculates the square of an integer through an AWS
Lambda function invoked from an API Gateway GET endpoint.

## Requirements

- Java 8 or later (the artifact is compiled with `--release 8`).
- Maven 3.6 or later.
- An AWS account with permission to create Lambda and API Gateway resources.
  AWS Academy users can use the `LabRole` execution role.

## Build and test locally

From the repository root:

```bash
mvn clean test
mvn package
```

The JAR to upload to Lambda is generated at
`target/hello-world-aws-lambda-api-gateway-java-1.0-SNAPSHOT.jar`.

## Create the Lambda function

1. In AWS Lambda, select **Create function** > **Author from scratch**.
2. Name the function `square`, select Java 8 (or an equivalent available Java
   runtime), and select `LabRole` or a custom execution role.
3. Upload the generated JAR in the **Code** section.
4. Set the handler to:

   ```text
   co.edu.escuelaing.services.MathServices::square
   ```

5. Create a test event whose content is only the number `5`, not a JSON object.
   The expected result is `25`.

The `square(Integer)` signature allows Lambda to deserialize the numeric value
as the handler's first parameter.

## Expose the function through API Gateway

1. Create a regional **REST** API named `mathServices`.
2. Create a **GET** method and select the `square` Lambda function.
3. In **Method Request**, add the `value` query string parameter.
4. In **Integration Request**, configure the input parameter `value` with
   `method.request.querystring.value`.
5. Add a mapping template for the request content type (for example,
   `application/json`) with the following content:

   ```velocity
   $input.params("value")
   ```

   The template sends only the integer to Lambda, which is what the handler
   expects. Do not wrap it in a JSON object.
6. Test the method with `value=14`; the expected response is `196`.
7. Deploy the API to a stage, for example `Beta`, and call:

   ```text
   https://<api-id>.execute-api.<region>.amazonaws.com/Beta?value=14
   ```

If `value` is omitted, the mapping does not produce a valid integer and the
invocation should be treated as invalid input. Delete the Lambda function and
the API after completing the workshop to avoid unnecessary charges.

## Project structure

```text
src/main/java/co/edu/escuelaing/services/MathServices.java  # handler Lambda
src/test/java/.../MathServicesTest.java                     # unit tests
pom.xml                                                     # Maven build
```
