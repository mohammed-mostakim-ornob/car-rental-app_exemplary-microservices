package de.uniba.dsg.microservice.architecture.extraction.managementservice.service;

import de.uniba.dsg.microservice.architecture.extraction.managementservice.model.dto.*;
import de.uniba.dsg.microservice.architecture.extraction.managementservice.model.entity.*;
import de.uniba.dsg.microservice.architecture.extraction.managementservice.repository.*;
import microservice.architecture.extraction.managementservice.model.dto.*;
import microservice.architecture.extraction.managementservice.model.entity.*;
import microservice.architecture.extraction.managementservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class InstanceService {
    private final ContactRepository contactRepository;
    private final DomainRepository domainRepository;
    private final HostRepository hostRepository;
    private final MethodRepository methodRepository;
    private final ParameterRepository parameterRepository;
    private final RegionRepository regionRepository;
    private final ResponseRepository responseRepository;
    private final SchemaRepository schemaRepository;
    private final ServiceInstanceRepository serviceInstanceRepository;
    private final ServiceRepository serviceRepository;

    @Autowired
    public InstanceService(
            ContactRepository contactRepository,
            DomainRepository domainRepository,
            HostRepository hostRepository,
            MethodRepository methodRepository,
            ParameterRepository parameterRepository,
            RegionRepository regionRepository,
            ResponseRepository responseRepository,
            SchemaRepository schemaRepository,
            ServiceInstanceRepository serviceInstanceRepository,
            ServiceRepository serviceRepository
    ) {
        this.contactRepository = contactRepository;
        this.domainRepository = domainRepository;
        this.hostRepository = hostRepository;
        this.methodRepository = methodRepository;
        this.parameterRepository = parameterRepository;
        this.regionRepository = regionRepository;
        this.responseRepository = responseRepository;
        this.schemaRepository = schemaRepository;
        this.serviceInstanceRepository = serviceInstanceRepository;
        this.serviceRepository = serviceRepository;
    }

    public void addInstance(ServiceInstanceDto instanceDto) {
        ServiceInstance newServiceInstance = new ServiceInstance(
                null,
                instanceDto.getPort(),
                instanceDto.getBasePath(),
                instanceDto.getContainerId(),
                instanceDto.getTechnology(),
                instanceDto.getProtocols(),
                instanceDto.getStartTime(),
                instanceDto.getEndTime(),
                getHost(instanceDto.getHost(), instanceDto.getRegion()),
                serviceRepository.findByTitleAndVersion(instanceDto.getService().getTitle(), instanceDto.getService().getVersion())
                        .orElseGet(() -> addService(instanceDto.getService()))
        );
        serviceInstanceRepository.save(newServiceInstance);
    }

    private de.uniba.dsg.microservice.architecture.extraction.managementservice.model.entity.Service addService(ServiceDto serviceDto) {
        de.uniba.dsg.microservice.architecture.extraction.managementservice.model.entity.Service service
                = new de.uniba.dsg.microservice.architecture.extraction.managementservice.model.entity.Service(
                        null,
                        serviceDto.getTitle(),
                        serviceDto.getVersion(),
                        serviceDto.getDescription(),
                        serviceDto.getTechnologyStack(),
                        serviceDto.getCreationDate(),
                        getContact(serviceDto.getContactName(), serviceDto.getContactEmail()),
                        getDomain(serviceDto.getDomainName()),
                        addMethods(serviceDto.getMethods())
                );
        serviceRepository.save(service);

        return service;
    }

    private Set<Method> addMethods(Set<MethodDto> methodDtos) {
        Set<Method> addedMethods = new HashSet<>();

        methodDtos.forEach(method -> {
            Method newMethod = new Method(
                    null,
                    method.getPath(),
                    method.getMethod(),
                    method.getSummary(),
                    method.getDescription(),
                    addParameters(method.getParameters()),
                    addResponses(method.getResponses())
            );
            methodRepository.save(newMethod);

            addedMethods.add(newMethod);
        });

        return addedMethods;
    }

    private Set<Parameter> addParameters(Set<ParameterDto> parameterDtos) {
        Set<Parameter> addedParameters = new HashSet<>();

        parameterDtos.forEach(param -> {
            Parameter newParameter = new Parameter(
                    null,
                    param.getLocation(),
                    param.getName(),
                    param.getDescription(),
                    param.getDefaultValue(),
                    param.isRequired(),
                    addSchema(param.getSchema())
            );
            parameterRepository.save(newParameter);

            addedParameters.add(newParameter);
        });

        return addedParameters;
    }

    private Set<Response> addResponses(Set<ResponseDto> responseDtos) {
        Set<Response> addedReponse = new HashSet<>();

        responseDtos.forEach(res -> {
            Response newResponse = new Response(
                    null,
                    res.getCode(),
                    res.getDescription(),
                    addSchema(res.getSchema())
            );
            responseRepository.save(newResponse);

            addedReponse.add(newResponse);
        });

        return addedReponse;
    }

    private Schema addSchema(SchemaDto schemaDto) {
        Schema newSchema = new Schema(
                null,
                schemaDto.getName(),
                schemaDto.getType()
        );
        schemaRepository.save(newSchema);

        return newSchema;
    }

    private Contact getContact(String name, String email) {
        Optional<Contact> contact = contactRepository.findByName(name);

        if (contact.isPresent())
            return contact.get();

        Contact newContact = new Contact(null, name, email);
        contactRepository.save(newContact);

        return newContact;
    }

    private Domain getDomain(String name) {
        Optional<Domain> domain = domainRepository.findByName(name);

        if (domain.isPresent())
            return domain.get();

        Domain newDomain = new Domain(null, name);
        domainRepository.save(newDomain);

        return newDomain;
    }

    private Host getHost(String name, String region) {
        Optional<Host> host = hostRepository.findByName(name);

        if (host.isPresent())
            return host.get();

        Host newHost = new Host(
                null,
                name,
                getRegion(region)
        );
        hostRepository.save(newHost);

        return newHost;
    }

    private Region getRegion(String name) {
        Optional<Region> region = regionRepository.findByName(name);

        if (region.isPresent())
            return region.get();

        Region newRegion = new Region(null, name);
        regionRepository.save(newRegion);

        return newRegion;
    }
}
