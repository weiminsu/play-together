FROM public.ecr.aws/lambda/java:11.2022.04.22.18-x86_64

RUN yum install -y zip unzip
COPY build/classes ${LAMBDA_TASK_ROOT}
ADD build/distributions/image-server.zip /tmp/image-server.zip
RUN unzip /tmp/image-server.zip
RUN mv image-server/lib ${LAMBDA_TASK_ROOT}/
RUN rm -rf image-server


CMD ["au.edu.rmit.student.weiminsu.cosc2626.playtogether.image.handler.GetImageRequestHandler::handleRequest"]