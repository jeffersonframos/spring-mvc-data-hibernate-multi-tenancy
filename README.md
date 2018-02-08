# spring-mvc-data-hibernate-multi-tenancy

Implementation of Hibernate Multi Tenancy using Sprimg MVC and Spring Data JPA.
In the PersistenceContext file is defined the custom DataSource using the file in the @PropertySource annotation and also is defined the configuration to enable the multi tenancy to Hibernate.

All the related files to configure the multi tenancy are under the com.ramos.f.jefferson.tenant package.
The TenantInterceptor file is the responsible for the selection of the data sources, using the sub domainas the tenant ID to be selected. It can also be done by using a parameter in the request.

<h2>Future implementations</h2>
Use Spring Security to protected the REST end points and set the tenant ID based on the logged user.
