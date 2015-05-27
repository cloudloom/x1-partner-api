package com.tracebucket.x1.partner.api.test.repository;

import com.tracebucket.x1.partner.api.DefaultPartnerStarter;
import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.domain.Affiliate;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultAffiliate;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartner;
import com.tracebucket.x1.partner.api.repository.jpa.DefaultPartnerRepository;
import com.tracebucket.x1.partner.api.test.fixture.DefaultAddressFixture;
import com.tracebucket.x1.partner.api.test.fixture.DefaultAffiliateFixture;
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

import javax.transaction.Transactional;

/**
 * Created by Vishwajit on 26-05-2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes  = {DefaultPartnerStarter.class})
@Transactional
public class PartnerRepositoryTest {

    @Autowired
    private DefaultPartnerRepository partnerRepository;

    private DefaultPartner partner = null;

    private void createPartner() throws Exception{
        partner = DefaultPartnerFixture.standardPartner();
        partner = partnerRepository.save(partner);
    }

    @Test
    @Rollback(value = false)
    public void testCreate() throws Exception{
        createPartner();
        Assert.assertNotNull(partner);
    }

    @Test
    @Rollback(value = false)
    public void testUpdate() throws Exception {
        createPartner();
        partner.setPartnerCategory(PartnerCategory.GROUP);
        partner.addPartnerRole(DefaultAffiliateFixture.standardAffiliate());
        partner = partnerRepository.save(partner);
        Assert.assertNotNull(partner);
        Assert.assertNotNull(partner.getAggregateId());
        Assert.assertEquals(PartnerCategory.GROUP, partner.getPartnerCategory());
        Assert.assertEquals(1, partner.getAllAssignedRoles().size());
    }

    @Test
    @Rollback(value = false)
    public void testFindById() throws Exception {
        createPartner();
        partner = partnerRepository.findOne(partner.getAggregateId());
        Assert.assertNotNull(partner);
    }

    @After
    public void tearDown(){
        if(partner != null && partner.getAggregateId() != null) {
            partnerRepository.delete(partner.getAggregateId());
            partner = partnerRepository.findOne(partner.getAggregateId());
            Assert.assertNull(partner);
        }
    }
}
