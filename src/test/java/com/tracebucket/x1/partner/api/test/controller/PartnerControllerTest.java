package com.tracebucket.x1.partner.api.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracebucket.x1.partner.api.DefaultPartnerStarter;
import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.rest.resources.DefaultAddressResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultAffiliateResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPartnerResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPartnerRoleResource;
import com.tracebucket.x1.partner.api.test.fixture.DefaultAddressResourceFixture;
import com.tracebucket.x1.partner.api.test.fixture.DefaultAffiliateResourceFixture;
import com.tracebucket.x1.partner.api.test.fixture.DefaultPartnerResourceFixture;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

/**
 * Created by Vishwajit on 27-05-2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultPartnerStarter.class)
@WebIntegrationTest
public class PartnerControllerTest {

    private static final Logger log = LoggerFactory.getLogger(PartnerControllerTest.class);

    private RestTemplate restTemplate = null;

    @Value("http://localhost:${server.port}${server.contextPath}")
    private String basePath;

    @Autowired
    private ObjectMapper objectMapper;

    private DefaultPartnerResource partner = null;

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
    }

    private void createPartner() throws Exception{
        partner = DefaultPartnerResourceFixture.standardPartner();
        log.info("Create Partner : " + objectMapper.writeValueAsString(partner));
        partner = restTemplate.postForObject(basePath+"/partner", partner, DefaultPartnerResource.class);
        Assert.assertNotNull(partner);
    }

    @Test
    public void testCreate() throws Exception {
        createPartner();
        Assert.assertNotNull(partner.getUid());
    }

    @Test
    public void testFindOne() throws Exception {
        createPartner();
        String uid = partner.getUid();
        log.info("Find Partner with UID : " + partner.getUid());
        partner = restTemplate.getForObject(basePath + "/partner/" + uid, DefaultPartnerResource.class);
        Assert.assertNotNull(partner);
        Assert.assertNotNull(partner.getUid());
        Assert.assertEquals(uid, partner.getUid());
        log.info("Found : " + objectMapper.writeValueAsString(partner));
    }

    @Test
    public void testSetPartnerCategory() throws Exception {
        createPartner();
        restTemplate.put(basePath+"/partner/"+partner.getUid()+"/partnercategory", PartnerCategory.GROUP);
        partner = restTemplate.getForObject(basePath + "/partner/" + partner.getUid(), DefaultPartnerResource.class);
        Assert.assertNotNull(partner.getUid());
        Assert.assertEquals(PartnerCategory.GROUP, partner.getPartnerCategory());
    }

    @Test
    public void testMovePartnerCategory() throws Exception {
        createPartner();
        restTemplate.put(basePath + "/partner/" + partner.getUid() + "/partner/tocategory", PartnerCategory.INDIVIDUAL);
        partner = restTemplate.getForObject(basePath + "/partner/" + partner.getUid(), DefaultPartnerResource.class);
        Assert.assertNotNull(partner.getUid());
        Assert.assertEquals(PartnerCategory.INDIVIDUAL, partner.getPartnerCategory());
    }

/*    @Test
    public void testAddPartnerRole() throws Exception {
        createPartner();
        DefaultAffiliateResource defaultAffiliateResource = DefaultAffiliateResourceFixture.standardAffiliate();
        log.info("Add Partner Role : " + objectMapper.writeValueAsString(defaultAffiliateResource));
        restTemplate.put(basePath+"/partner/"+partner.getUid()+"/partnerrole", (DefaultPartnerRoleResource)defaultAffiliateResource);
        partner = restTemplate.getForObject(basePath + "/partner/" + partner.getUid(), DefaultPartnerResource.class);
        Assert.assertNotNull(partner.getUid());
        Assert.assertEquals(1, partner.getPartnerRoles().size());
    }*/

/*    @Test
    public void testAddAddressToRole() throws Exception {
        createPartner();
        DefaultAffiliateResource defaultAffiliateResource = DefaultAffiliateResourceFixture.standardAffiliate();
        log.info("Add Partner Role : " + objectMapper.writeValueAsString(defaultAffiliateResource));
        restTemplate.put(basePath+"/partner/"+partner.getUid()+"/partnerrole", (DefaultPartnerRoleResource)defaultAffiliateResource);
        partner = restTemplate.getForObject(basePath + "/partner/" + partner.getUid(), DefaultPartnerResource.class);
        Assert.assertNotNull(partner.getUid());
        Assert.assertEquals(1, partner.getPartnerRoles().size());
        DefaultAddressResource addressResource = DefaultAddressResourceFixture.standardAddress();
        Set<DefaultPartnerRoleResource> roleResourceSet = partner.getPartnerRoles();
        if(roleResourceSet != null) {
            for(DefaultPartnerRoleResource partnerRoleResource : roleResourceSet) {
                restTemplate.put(basePath+"/partner/"+partner.getUid()+"/partnerRole/"+partnerRoleResource.getUid(), addressResource);
                partner = restTemplate.getForObject(basePath + "/partner/" + partner.getUid(), DefaultPartnerResource.class);
                Assert.assertNotNull(partner.getUid());
                Assert.assertEquals(1, partner.getPartnerRoles().size());
            }
        }

    }*/

/*    @Test
    public void testChangeOwner() throws Exception {

    }*/

    @Test
    public void testMoveRoleAddressTo() throws Exception {
        //TODO
    }

    @Test
    public void testHasPartnerRole() throws Exception {
        //TODO
    }
}
