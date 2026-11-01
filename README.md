# Hello World: AWS Lambda and API Gateway in Java

Implementation of the workshop from Escuela Colombiana de Ingeniería Julio
Garavito. The service calculates the square of an integer through an AWS
Lambda function invoked from an API Gateway GET endpoint.

## Requirements

- Java 25 LTS (the artifact is compiled with `--release 25`).
- Maven 3.9 or later.
- An AWS account with permission to create Lambda and API Gateway resources.
  AWS Academy users can use the `LabRole` execution role.

If several JDKs are installed, make sure `JAVA_HOME` points to JDK 25 before
building, otherwise Maven fails with `release version 25 not supported`.

![Java and Maven versions](docs/images/01-java-maven-versions.png)

## Build and test locally

From the repository root:

```bash
mvn clean test
mvn package
```

All three unit tests pass:

![mvn clean test](docs/images/02-mvn-clean-test.png)

The JAR to upload to Lambda is generated at
`target/hello-world-aws-lambda-api-gateway-java-1.0-SNAPSHOT.jar`; it contains
only `MathServices.class`, so no extra dependencies need to be bundled.

![mvn package](docs/images/03-mvn-package.png)

## Create the Lambda function

1. In AWS Lambda, select **Create function** > **Author from scratch**.
2. Name the function `square`, select the Java 25 runtime (or the equivalent
   current AWS-managed Java runtime), and select `LabRole` or a custom
   execution role.
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

## AWS console evidence

The AWS console steps cannot be captured from the command line. Add the
following screenshots to `docs/images/` and reference them in the sections
above to complete the lab report:

| File | What it shows |
|------|---------------|
| `04-lambda-function.png` | `square` function created with the Java 25 runtime and the handler configured |
| `05-lambda-test.png` | Test event `5` returning `25` |
| `06-apigw-method-request.png` | `value` query string parameter in **Method Request** |
| `07-apigw-mapping-template.png` | Mapping template `$input.params("value")` |
| `08-apigw-test.png` | API Gateway test with `value=14` returning `196` |
| `09-apigw-deploy-stage.png` | API deployed to the `Beta` stage |
| `10-browser-result.png` | Browser calling the invoke URL with `?value=14` |
| `11-cleanup.png` | Lambda function and API deleted |

## Project structure

```text
src/main/java/co/edu/escuelaing/services/MathServices.java  # handler Lambda
src/test/java/.../MathServicesTest.java                     # unit tests
pom.xml                                                     # Maven build
docs/images/                                                # lab screenshots
```
