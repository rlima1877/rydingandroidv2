package edu.temple.materialdesigntest;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import edu.temple.materialdesigntest.activities.DriverActivity;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentalss</a>
 */
public class DriverActivityTest extends ActivityInstrumentationTestCase2<DriverActivity> {
    private DriverActivity activity;
    private EditText busIDText;

    public DriverActivityTest() {
        super(DriverActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        busIDText = (EditText) activity.findViewById(R.id.busid);
    }

    public void testPreconditions() {
        assertNotNull("activity is null", activity);
        assertNotNull("busIDText is null", busIDText);
    }

    public void testBusIDIsInteger() {
        int busID;

        //test empty string
        busIDText.setText("");
        try {
            busID = activity.getBusID();
            fail("Empty string gave bus ID " + busID + ", expected exception");
        } catch (NumberFormatException e) {
        }

        //test letters
        busIDText.setText("abcd");
        try {
            busID = activity.getBusID();
            fail("abcd gave bus ID " + busID + ", expected exception");
        } catch (NumberFormatException e) {
        }

        //test decimal
        busIDText.setText("0.5");
        try {
            busID = activity.getBusID();
            fail("0.5 gave bus ID " + busID + ", expected exception");
        } catch (NumberFormatException e) {
        }

        //test 0
        busIDText.setText("0");
        try {
            busID = activity.getBusID();
            assertEquals(0, busID);
        } catch (NumberFormatException e) {
            fail("0 threw exception");
        }

        //test 1
        busIDText.setText("1");
        try {
            busID = activity.getBusID();
            assertEquals(1, busID);
        } catch (NumberFormatException e) {
            fail("1 threw exception");
        }

        //test -1
        //design docs simply say integer, so is this fine?
        busIDText.setText("-1");
        try {
            busID = activity.getBusID();
            assertEquals(-1, busID);
        } catch (NumberFormatException e) {
            fail("-1 threw exception");
        }
    }
}