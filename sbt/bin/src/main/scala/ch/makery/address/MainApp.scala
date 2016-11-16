package ch.makery.address
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{NoDependencyResolver, FXMLView, FXMLLoader}
import javafx.{scene => jfxs}
import scalafx.collections.{ObservableBuffer}
import ch.makery.address.model.{BankAccount, BasicBankAccount,PrivilegeBankAccount}
import scalafx.stage.{ Stage, Modality }
import ch.makery.address.view.BankAccountSoloviewController
import scalafx.event.ActionEvent


object MainApp extends JFXApp {
  // the data as an observable list of Persons
  val bankAccountData = new ObservableBuffer[BankAccount]()
  
    bankAccountData += new PrivilegeBankAccount("Hans", "Muster",50.0)
    bankAccountData += new BasicBankAccount("Ruth", "Mueller",100.0)
    bankAccountData += new BankAccount("Heinz", "Kurz",100.0)
    bankAccountData += new BankAccount("Cornelia", "Meier",200.0)
    bankAccountData += new BankAccount("Dockey", "Kong",2002.0)
    bankAccountData += new BankAccount("Hello123", "hahaha",234300.0)
    bankAccountData += new BankAccount("Corscaskia", "Measclier",2020.0)
    bankAccountData += new BankAccount("Cor", "Cassdsy",1230.0)
  
    val rootResource = getClass.getResource("view/RootLayout.fxml")
    val loader = new FXMLLoader(rootResource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.BorderPane]
    
    stage = new PrimaryStage {
      title = "BankApp"
      scene = new Scene {
        root = roots
      }
    }
    def showBankAccountOverview() = {
      val resource = getClass.getResource("view/BankAccountOverview.fxml")
      val loader = new FXMLLoader(resource, NoDependencyResolver)
      loader.load();
      val roots = loader.getRoot[jfxs.layout.AnchorPane]
      this.roots.setCenter(roots)
    } 
    
    def showBankAccountSoloOverview(bankAccount : BankAccount):Boolean={
      val resource= getClass.getResource("view/BankAccountSoloview.fxml")
      val loader=new FXMLLoader(resource,NoDependencyResolver)
      loader.load()
      val roots2=loader.getRoot[jfxs.Parent]
      val control=loader.getController[BankAccountSoloviewController#Controller]
      
      val dialog=new Stage(){
        initModality(Modality.APPLICATION_MODAL)
        initOwner(stage)
        scene=new Scene{
          root=roots2
        }
      }
      control.dialogStage=dialog
      control.bankaccount=bankAccount
      dialog.showAndWait()
      control.okClicked
      
    }
    
    showBankAccountOverview()
    
}