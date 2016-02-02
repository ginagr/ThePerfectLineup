package edu.wpi.www.theperfectlineup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ClipData;
import android.graphics.Typeface;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;

public class DropActivity extends AppCompatActivity {

    private int numOfRowers = 10;        //TODO fill with roster data
    private TextView[] portRowers = new TextView[numOfRowers];  //TODO add textView to portrower obj
    private TextView[] starRowers = new TextView[numOfRowers];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop);

        addRowers();

        addBoat();TextView temp = new TextView(this);
    }

    public void addRowers()
    {
        //TODO call either port or starboard method by id

        LinearLayout layout = (LinearLayout)findViewById(R.id.portRowerLinearLayout);
        layout.setScrollContainer(true);

        for (int i = 0; i < numOfRowers; i++)
        {
            portRowers[i] = new TextView(this);     //TODO initialize in portrower object
            portRowers[i].setText("Rower " + i);    //TODO change i to name attribute
            portRowers[i].setBackgroundResource(R.drawable.portrower);
            portRowers[i].setTextSize(1, 35);
            portRowers[i].setWidth(450);
            portRowers[i].setHeight(250);
            portRowers[i].setMaxLines(2);
            portRowers[i].setOnTouchListener(new ChoiceTouchListener());
            layout.addView(portRowers[i]);
        }

        LinearLayout layout2 = (LinearLayout)findViewById(R.id.starboardRowerLinearLayout);
        layout2.setScrollContainer(true);

        for (int i = 0; i < numOfRowers; i++)
        {
            starRowers[i] = new TextView(this);     //TODO initialize in portrower object
            starRowers[i].setText("Rower " + i);    //TODO change i to name attribute
            starRowers[i].setTextSize(1, 30);
            starRowers[i].setBackgroundResource(R.drawable.starboardrower);
            starRowers[i].setWidth(450);
            starRowers[i].setHeight(250);
            starRowers[i].setMaxLines(3);
            starRowers[i].setOnTouchListener(new ChoiceTouchListener());
            layout2.addView(starRowers[i]);
        }
    }

    public void addBoat()
    {
        LinearLayout layout = (LinearLayout)findViewById(R.id.boatLinearLayout);
        layout.setScrollContainer(true);
        layout.getLayoutParams().height = LayoutParams.WRAP_CONTENT;
        layout.getLayoutParams().width = LayoutParams.WRAP_CONTENT;
        TextView temp = new TextView(this);
        temp.setText("Coxswain");
        temp.setTextSize(1, 35);
        temp.setBackgroundResource(R.drawable.cox);
        temp.setWidth(250);
        temp.setHeight(150);
        temp.setOnDragListener(new ChoiceDragListener());
        layout.addView(temp);
        for (int i = 8; i > 0; i--)
        {
            temp = new TextView(this);
            if (i % 2 == 0) {
                temp.setText("" + i);
                temp.setBackgroundResource(R.drawable.starboardrower_shadow);
                temp.setHeight(250);
                temp.setTextSize(1, 35);
                temp.setOnDragListener(new ChoiceDragListener());
                layout.addView(temp);
            } else {
                temp.setText("" + i);
                temp.setBackgroundResource(R.drawable.portrower_shadow);
                temp.setHeight(250);
                temp.setTextSize(1, 35);
                temp.setOnDragListener(new ChoiceDragListener());
                layout.addView(temp);
            }
        }
    }


    private final class ChoiceTouchListener implements OnTouchListener {

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                //start dragging the item touched
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            }
            else {
                return false;
            }
        }
    }

    private class ChoiceDragListener implements OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:
                    //handle the dragged view being dropped over a target view
                    View view = (View) event.getLocalState();
                    //grey out oar
                    view.setAlpha((float)0.5);
                    //view dragged item is being dropped on
                    TextView dropTarget = (TextView) v;
                    //view being dragged and dropped
                    TextView dropped = (TextView) view;
                    //update the text in the target view to reflect the data being dropped
                    dropTarget.setText(dropped.getText());
                    //  String temp = dropped.getText().toString();
                    // temp = temp.charAt(temp.length()-1);
                    //  int temp2 = Integer.parseInt(temp);
                    if(dropped.getMaxLines() == 2){
                        dropTarget.setBackgroundResource(R.drawable.portrower);
                    }
                    else {
                        dropTarget.setBackgroundResource(R.drawable.starboardrower);
                    }
                    dropTarget.setHeight(202);
                    dropTarget.setWidth(20);
                    dropTarget.setPaddingRelative(0, 0, 0, 0);
                    //make it bold to highlight the fact that an item has been dropped
                    dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
                    //if an item has already been dropped here, there will be a tag
                    Object tag = dropTarget.getTag();
                    //if there is already an item here, set it back visible in its original place
                    if(tag!=null)
                    {
                        //the tag is the view id already dropped here
                        int existingID = (Integer)tag;
                        //set the original view visible again
                        findViewById(existingID).setVisibility(View.VISIBLE);
                    }
                    //set the tag in the target view to the ID of the view being dropped
                    dropTarget.setTag(dropped.getId());

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //no action necessary
                    break;
                default:
                    break;
            }

            return true;
        }
    }

}

