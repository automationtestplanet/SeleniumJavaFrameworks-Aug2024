package openmrs.test;

import org.openmrs.demo.page.objects.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OpenMrsKeywordDrivenTests extends OpenMrsTestNgBaseClass {

    @Test
    public void registerPatient() {
        Assert.assertTrue(homePage.verifyTileTile("Register a patient"));
        homePage.clickTile("Register a patient");
        Assert.assertTrue(registerPage.verifyPageName("Register a patient"));
        registerPage.enterRegistrationDetails("ATP, Test1", "Male", "01, January, 1990", "SRNagar, Hyderabad, Telangana, India, 500038", "9876543211");
        Assert.assertTrue(registerPage.verifyDetailsToConfirm("ATP, Test1", "Male", "01, January, 1990", "9876543211"));
        registerPage.clickConfirm();
        Assert.assertTrue(detailsPage.verifyPatientNameInPatientDetailsPage("ATP, Test1"));
        Assert.assertNotNull(detailsPage.getPatientId());
    }

    @Test
    public void findPatient() {
        homePage.clickTile("Find Patient Record");
        Assert.assertTrue(findPatientPage.verifyPageName("Find Patient Record"));
        findPatientPage.enterValueInPatientSearch("ATP Test1");
        Assert.assertTrue(findPatientPage.getFindPatientTableColumnValue("Name").equalsIgnoreCase("ATP Test1"));
        findPatientPage.clickFindPatientTableFirstRecord();
        Assert.assertTrue(detailsPage.verifyPatientNameInPatientDetailsPage("ATP, Test1"));
    }

    @Test
    public void startVisitsAndAddAttachments() {
        homePage.clickTile("Find Patient Record");
        findPatientPage.enterValueInPatientSearch("ATP Test1");
        findPatientPage.clickFindPatientTableFirstRecord();
        detailsPage.clickStartVisit();
        Assert.assertTrue(detailsPage.verifyVisitsTab());
        detailsPage.clickAttachments();
        String filePath = Utils.getTestProperty("upload.files.path") + "UploadFile.pdf";
        detailsPage.uploadFile(filePath, "Test1");
        Assert.assertTrue(detailsPage.verifyFileUpload("Test1"));
    }

    @Test
    public void deletePatient() {
        homePage.clickTile("Find Patient Record");
        findPatientPage.enterValueInPatientSearch("ATP Test1");
        findPatientPage.clickFindPatientTableFirstRecord();
        detailsPage.clickDeletePatient();
        detailsPage.enterDeleteReason("Other");
        detailsPage.clickDeletePatientConfirmButton();
        findPatientPage.enterValueInPatientSearch("ATP Test1");
        Assert.assertTrue(findPatientPage.verifyNoMatchRecordsFoundMessage());
    }
}
