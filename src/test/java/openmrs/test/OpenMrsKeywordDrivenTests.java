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
        findPatientPage.captureScreenshots();
        Assert.assertTrue(findPatientPage.verifyPageName("Find Patient Record"));
        findPatientPage.enterValueInPatientSearch("ATP Test1");
        findPatientPage.clearSearchField();
        findPatientPage.enterValueInPatientSearch("John Jade");
        findPatientPage.captureScreenshots();
//        Assert.assertTrue(findPatientPage.getFindPatientTableColumnValue("Name").equalsIgnoreCase("ATP Test1"));
        Assert.assertTrue(findPatientPage.getFindPatientTableColumnValue("Name").equalsIgnoreCase("John Jade"));
        findPatientPage.clickFindPatientTableFirstRecord();
        findPatientPage.captureScreenshots();
//        Assert.assertTrue(detailsPage.verifyPatientNameInPatientDetailsPage("ATP, Test1"));
        Assert.assertTrue(detailsPage.verifyPatientNameInPatientDetailsPage("John Jade"));

    }

    @Test
    public void startVisitsAndAddAttachments() {
        homePage.clickTile("Find Patient Record");
//        findPatientPage.enterValueInPatientSearch("ATP Test1");
        findPatientPage.enterValueInPatientSearch("John Jade");
        findPatientPage.captureScreenshots();
        findPatientPage.clickFindPatientTableFirstRecord();
        findPatientPage.captureScreenshots();
        detailsPage.clickStartVisit();
        findPatientPage.captureScreenshots();
        Assert.assertTrue(detailsPage.verifyVisitsTab());
        detailsPage.clickAttachments();
        findPatientPage.captureScreenshots();
        String filePath = System.getProperty("user.dir")+Utils.getTestProperty("upload.files.path") + "UploadFile.pdf";
        detailsPage.uploadFile(filePath, "Test1");
        findPatientPage.captureScreenshots();
        Assert.assertTrue(detailsPage.verifyFileUpload("Test1"));
    }

    @Test
    public void deletePatient() {
        homePage.clickTile("Find Patient Record");
//        findPatientPage.enterValueInPatientSearch("ATP Test1");
        findPatientPage.enterValueInPatientSearch("John Jade");
        findPatientPage.captureScreenshots();
        findPatientPage.clickFindPatientTableFirstRecord();
        findPatientPage.captureScreenshots();
        detailsPage.clickDeletePatient();
        findPatientPage.captureScreenshots();
        detailsPage.enterDeleteReason("Other");
        findPatientPage.captureScreenshots();
        detailsPage.clickDeletePatientConfirmButton();
        findPatientPage.captureScreenshots();
//        findPatientPage.enterValueInPatientSearch("ATP Test1");
        findPatientPage.enterValueInPatientSearch("John Jade");
        findPatientPage.captureScreenshots();
        Assert.assertTrue(findPatientPage.verifyNoMatchRecordsFoundMessage());
    }
}
