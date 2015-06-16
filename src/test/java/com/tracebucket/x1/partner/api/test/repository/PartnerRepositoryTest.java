package com.tracebucket.x1.partner.api.test.repository;

import com.tracebucket.x1.partner.api.DefaultPartnerStarter;
import com.tracebucket.x1.partner.api.domain.Partner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultOwner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartner;
import com.tracebucket.x1.partner.api.repository.jpa.DefaultPartnerRepository;
import com.tracebucket.x1.partner.api.test.fixture.DefaultPartnerFixture;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.UUID;

/**
 * Created by Vishwajit on 26-05-2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes  = {DefaultPartnerStarter.class})
public class PartnerRepositoryTest {

    @Autowired
    private DefaultPartnerRepository partnerRepository;

    private DefaultPartner partner = null;

    private void createPartner() throws Exception{
        String tenantId = UUID.randomUUID().toString();
        partner = DefaultPartnerFixture.standardPartnerWithOwner(new DefaultOwner(tenantId));
        partner = partnerRepository.save(partner);
    }

    @Test
    @Rollback(value = false)
    public void testCreate() throws Exception{
        createPartner();
        Assert.assertNotNull(partner);
        Assert.assertNotNull(partner.getOwner());
    }

    @Test
    @Rollback(value = false)
    public void testFindById() throws Exception {
        createPartner();
        partner = partnerRepository.findOne(partner.getAggregateId());
        Assert.assertNotNull(partner);
    }

    @Test
    @Rollback(value = false)
    public void testFindByTenantId() throws Exception {
        createPartner();
        partner = partnerRepository.findOne(partner.getAggregateId(), partner.getOwner().getOrganizationUID());
        Assert.assertNotNull(partner);
        Assert.assertNotNull(partner.getOwner());
        Assert.assertNotNull(partner.getOwner().getOrganizationUID());
        Assert.assertNull(partnerRepository.findOne(partner.getAggregateId(), UUID.randomUUID().toString()));
    }

    @Test
    @Rollback(value = false)
    public void testFindAllByTenantId() throws Exception {
        createPartner();
        List<DefaultPartner> partners = partnerRepository.findAll(partner.getOwner().getOrganizationUID());
        Assert.assertNotNull(partners);
        Assert.assertEquals(1, partners.size());
    }

    //@After
    public void tearDown(){
        if(partner != null && partner.getAggregateId() != null) {
            partnerRepository.delete(partner.getAggregateId(), partner.getOwner().getOrganizationUID());
            partner = partnerRepository.findOne(partner.getAggregateId(), partner.getOwner().getOrganizationUID());
            Assert.assertNull(partner);
        }
    }
}
