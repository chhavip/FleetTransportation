package fleet.logisure.chhavi.tflapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import it.neokree.materialnavigationdrawer.util.Utils;

public class MainActivity extends MaterialNavigationDrawer {

    MaterialAccount account;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void init(Bundle bundle) {
        account = new MaterialAccount(this.getResources(), "User ABCXYZ",null,null,R.drawable.abc_btn_check_material);
        this.addAccount(account);
        this.addSection(newSection("Home", R.drawable.abc_btn_radio_material, new HomeFragment()));
        this.addSection(newSection("Take Attendace", R.drawable.abc_btn_radio_material, new AttendanceFragment()));
        this.addSection(newSection("Upload Images", R.drawable.abc_btn_radio_material, new UploadImagesFragment()));
        this.addSection(newSection("Call Command Center", R.drawable.abc_btn_radio_material, new CallCommandCenterFragment()));
        this.addSection(newSection("Feedback", R.drawable.abc_btn_radio_material, new FeedbackFragment()));
        this.addSection(newSection("Help", R.drawable.abc_btn_radio_material, new HelpFragment()));
        this.addSection(newSection("Cash in hand", R.drawable.abc_btn_radio_material, new CashInHandFragment()));
        this.addSection(newSection("Monthly Attendance", R.drawable.abc_btn_radio_material, new MonthlyAttendanceFragment()));
        this.addSection(newSection("Diesel Summary", R.drawable.abc_btn_radio_material, new DieselSummaryFragment()));




       // this.addSection(newSection("Take Attendace", R.drawable.abc_btn_radio_material, new Pro()));
      /*  this.addSection(newSection("Bhartiya Janta Party", R.drawable.abc_btn_radio_material , new PartyFragment()));
        this.addSection(newSection("Congress", R.drawable.abc_btn_radio_material , new CongressFragment()));
        this.addSection(newSection("About Us", R.drawable.abc_btn_radio_material, new AboutUs()));*/
    }
}
