package stc.test.socialmedia.config;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import stc.test.socialmedia.friendshiprequest.model.FriendshipRequest;
import stc.test.socialmedia.friendshiprequest.to.FriendshipRequestTo;
import stc.test.socialmedia.messaging.to.DialogRequestTo;
import stc.test.socialmedia.messaging.model.DialogRequest;
import stc.test.socialmedia.post.model.Post;
import stc.test.socialmedia.user.model.User;
import stc.test.socialmedia.user.to.RequestUserTo;
import stc.test.socialmedia.user.to.ResponseUserTo;

@Configuration

public class OpenApiConfig {


    @Bean
    GroupedOpenApi token() {
        return GroupedOpenApi.builder()
                .group("JWT Token")
                .addOpenApiCustomizer(openApi -> {
                    openApi.addSecurityItem(new SecurityRequirement().addList("Authorization"))
                            .components(new Components()
                                    .addSecuritySchemes("Authorization", new SecurityScheme()
                                            .in(SecurityScheme.In.HEADER)
                                            .type(SecurityScheme.Type.HTTP)
                                            .scheme("basic"))
                            )
                            .info(new Info().title("JWT Token").description("""
                                    API для получения токена
                                    <p>Тестовые креденшелы:<br>
                                       - user@yandex.ru.com / password<br>
                                       - admin@gmail.com / admin
                                    </p>
                                    """));

                })
                .pathsToMatch("/token")
                .build();
    }

    @Bean
    GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("REST API")
                .addOpenApiCustomizer(openApi -> {
                    openApi.addSecurityItem(new SecurityRequirement().addList("Authorization"))
                            .components(new Components()
                                    .addSchemas("User", ModelConverters.getInstance().readAllAsResolvedSchema(User.class).schema)
                                    .addSchemas("ResponseUserTo", ModelConverters.getInstance().readAllAsResolvedSchema(ResponseUserTo.class).schema)
                                    .addSchemas("RequestUserTo", ModelConverters.getInstance().readAllAsResolvedSchema(RequestUserTo.class).schema)
                                    .addSchemas("Post", ModelConverters.getInstance().readAllAsResolvedSchema(Post.class).schema)
                                    .addSchemas("FriendshipRequest", ModelConverters.getInstance().readAllAsResolvedSchema(FriendshipRequest.class).schema)
                                    .addSchemas("FriendshipRequestTo", ModelConverters.getInstance().readAllAsResolvedSchema(FriendshipRequestTo.class).schema)
                                    .addSchemas("DialogRequest", ModelConverters.getInstance().readAllAsResolvedSchema(DialogRequest.class).schema)
                                    .addSchemas("DialogRequestTo", ModelConverters.getInstance().readAllAsResolvedSchema(DialogRequestTo.class).schema)
                                    .addSecuritySchemes("Authorization", new SecurityScheme()
                                            .in(SecurityScheme.In.HEADER)
                                            .type(SecurityScheme.Type.HTTP)
                                            .scheme("bearer")
                                            .name("JWT"))
                            )
                            .info(new Info().title("REST API").version("1.0").description("""
                                    RESTful API для социальной медиа платформы, позволяющей пользователям регистрироваться, входить в систему, создавать посты, переписываться, подписываться на других пользователей и получать свою ленту активности. </a>
                                    <p>Авторизация через JWT Token (справа верху `Select a definition`)</p>
                                    """));
                })
                .pathsToMatch("/api/**")
                .build();
    }
}
