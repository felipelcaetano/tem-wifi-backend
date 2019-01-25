package br.com.temwifi.utils;

import br.com.temwifi.annotations.Controller;
import br.com.temwifi.annotations.Entity;
import br.com.temwifi.annotations.Service;
import br.com.temwifi.domains.infra.enums.InjectablesEnum;
import br.com.temwifi.domains.infra.utils.factory.DynamoDBMapperFactory;
import br.com.temwifi.domains.infra.utils.factory.DynamoDBTableFactory;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.InternalServerErrorException;
import com.amazonaws.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class InstanceUtils {

    private static final Logger LOGGER = LogManager.getLogger(InstanceUtils.class);

    private static final String CONTROLLER = "Controller";
    private static final String SERVICE = "Service";
    private static final String ENTITY = "Entity";

    public static void instatiateIjectables(Object object, InjectablesEnum injectable)
            throws IllegalAccessException, InstantiationException {

        Class<? extends Annotation> annotation;
        InjectablesEnum nextInjectable;
        switch (injectable.getInjectableName()) {
            case CONTROLLER:
                Optional<Controller> controller = Optional.ofNullable(object.getClass().getAnnotation(Controller.class));

                if (!controller.isPresent()) {
                    throw new InternalServerErrorException(String.format("Controller [%s] sem annotation Controller",
                            object.getClass().getSimpleName()));
                }

                nextInjectable = InjectablesEnum.SERVICE;
                annotation = Service.class;
                break;
            case SERVICE:
                nextInjectable = InjectablesEnum.ENTITY;
                annotation = Entity.class;
                break;
            case ENTITY:
                nextInjectable = null;
                annotation = null;
                break;
            default:
                return;
        }

        if (injectable.equals(InjectablesEnum.CONTROLLER) || injectable.equals(InjectablesEnum.SERVICE)) {
            List<Field> fields = Arrays.asList(object.getClass().getDeclaredFields());

            for (Field field : fields) {
                if (field.getType().isAnnotationPresent(annotation)) {
                    field.setAccessible(true);
                    Object instance = field.get(object);

                    if (instance == null) {
                        instance = field.getType().newInstance();
                    }

                    field.set(object, instance);
                    InstanceUtils.instatiateIjectables(instance, nextInjectable);
                }
            }
        }

        if(injectable.equals(InjectablesEnum.ENTITY)) {

            if(!object.getClass().isAnnotationPresent(Entity.class)) {
                throw new InternalServerErrorException(String.format("Entity [%s] sem annotation @ENTITY",
                        object.getClass().getSimpleName()));
            }

            List<Field> fields = Arrays.asList(object.getClass().getSuperclass().getDeclaredFields());
            String tableName = "";
            for (Field field : fields) {
                field.setAccessible(true);

                if(field.getType().equals(Table.class)) {

                    Object instance = field.get(object);

                    if(instance == null) {
                        tableName = object.getClass().getAnnotation(Entity.class).tableName();
                        instance = DynamoDBTableFactory.getTable(tableName);
                    }

                    field.set(object, instance);
                }

                if(field.getType().equals(DynamoDBMapper.class)) {

                    Object instance = field.get(object);

                    if(instance == null) {
                        instance = DynamoDBMapperFactory.getMapper();
                    }

                    field.set(object, instance);
                }
            }

            fields = Arrays.asList(object.getClass().getDeclaredFields());
            for (Field field : fields) {
                field.setAccessible(true);

                if (!StringUtils.isNullOrEmpty(tableName) && field.getType().equals(Index.class)) {

                    Object instance = field.get(object);

                    if (instance == null) {
                        String indexName = object.getClass().getAnnotation(Entity.class).indexes()[0];
                        instance = DynamoDBTableFactory.getIndex(tableName, indexName);
                    }

                    field.set(object, instance);
                }
            }
        }
    }
}
