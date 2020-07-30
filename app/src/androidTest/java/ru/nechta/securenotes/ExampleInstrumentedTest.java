package ru.nechta.securenotes;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import ru.nechta.securenotes.Database.DataBase;
import ru.nechta.securenotes.Database.MessageRecord;
import ru.nechta.securenotes.Interface.MainActivity;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("ru.nechta.securenotes", appContext.getPackageName());
    }
    @Test
    public void BaseAdd(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        MainActivity activity = new MainActivity();
        activity.AES.secret="";
        DataBase db=new DataBase(appContext);
        db.AddRecord(-1,1,"C","M");
        db.ReadDB();
        MessageRecord m=db.Records.get(0);
        assertEquals("Record adding",m,new MessageRecord(0,1,"C","M"));
    }


}
