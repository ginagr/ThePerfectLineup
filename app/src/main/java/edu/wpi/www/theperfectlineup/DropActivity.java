package edu.wpi.www.theperfectlineup;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ClipData;
import android.view.Display;
import android.view.DragEvent;
import com.github.siyamed.shapeimageview.CircularImageView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;



public class DropActivity extends AppCompatActivity {

    private User mUser = new User("Rowing");//TODO this is a mock user with rowing.

    private Athlete[] mAthletesLeft = new Athlete[]{
        new Athlete("Athlete 1", 21),
                new Athlete("Athlete 2", 24),
                new Athlete("Athlete 3", 21),
                new Athlete("Athlete 4", 27),
                new Athlete("Tim 5", 18),
                new Athlete("Athlete 6", 17),
                new Athlete("Athlete 7", 15),
                new Athlete("Athlete 8", 19),
                new Athlete("Athlete 9", 17),
                new Athlete("Athlete 10", 17),
                new Athlete("Athlete 11", 20),
                new Athlete("Athlete 12", 22),
                new Athlete("Athlete 13", 21),}; //TODO remove an dreplace with actual database. This is a mock


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop);
        Point size = new Point();
        Display display = this.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        int width = size.x;//Width of display
        int height = size.y;//height of display
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.leftBracketLinearLayout);
        addAthletes(layout1, mAthletesLeft);
        addBoat(height, 8);//TODO will have to get the boatSize from the chosen boat
    }

    public void alertUser (String title,String msg, final Runnable ifTrue, final Runnable ifFalse) {
        final boolean mResult = false;//default as false
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        ifTrue.run();

                    }})
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ifFalse.run();
                    }
                }).show();
    }

//    This method adds all athletes in a roster to the view.
// It takes in a viewGroup we want to attach to and an array of athletes


    public void addAthletes(ViewGroup view, Athlete[] athleteArr) {
        view.setScrollContainer(true);
        for (int i = 0; i < athleteArr.length; i++) {
            LinearLayout[] viewAthletes = new LinearLayout[athleteArr.length];
            LayoutInflater inflater = LayoutInflater.from(this);
            LinearLayout profile = (LinearLayout)inflater.inflate(R.layout.bubble_profile, null);
            profile.setOrientation(LinearLayout.VERTICAL);
            CircularImageView profile_circle = (CircularImageView) profile.findViewById(R.id.athlete_image_view);
            TextView profile_text = (TextView) profile.findViewById (R.id.athlete_text_view);
            profile_text.setText(athleteArr[i].getFirstName()+athleteArr[i].getLastName());
            Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.profile_image, null);
            profile_circle.setImageDrawable(d);
            viewAthletes[i]= profile;
            viewAthletes[i].setId(989023490 + i);//needed some unique tag for athletes.
            viewAthletes[i].setOnTouchListener(new ChoiceTouchListener());
            view.addView(viewAthletes[i]);
        }
    }



    public void addBoat(int height, int boatSize)
    {
        LinearLayout layout = (LinearLayout) findViewById(R.id.lineupLinearLayout);

        layout.setScrollContainer(true);
        int effectiveHeight = height-(height/100*24);//14% higher than the lowest point to avoid statusbar
        int margin = (effectiveHeight/100);//1% margin
        int diameter = (effectiveHeight/boatSize)-(2*margin);
        LinearLayout.LayoutParams dynamicParams = new LinearLayout.LayoutParams(diameter,diameter);

        for (int i = boatSize; i >= 0; i--)
        {
            LayoutInflater inflater = LayoutInflater.from(this);
            LinearLayout profile = (LinearLayout)inflater.inflate(R.layout.bubble_profile, null);
            CircularImageView profile_circle = (CircularImageView) profile.findViewById(R.id.athlete_image_view);
            TextView profile_text = (TextView) profile.findViewById (R.id.athlete_text_view);
            LinearLayout.LayoutParams layParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layParams.setMargins(0, margin, 0, margin);
            profile.setLayoutParams(layParams);
            //profile.setGravity(Gravity.CENTER_HORIZONTAL);
            profile_text.setText("Athlete "+i);//TODO set to sport of coach
            profile_circle.setLayoutParams(dynamicParams);
            profile.setOnDragListener(new ChoiceDragListener());
            layout.addView(profile);

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

        //A helper to reset the view
        private void resetView (View view) {
            view.setAlpha(1);
            view.setEnabled(true);
        }

        private void setAthlete(View dropped, View dropTargetView){
            CircularImageView profile_circle_dropTarget = (CircularImageView) dropTargetView.findViewById (R.id.athlete_image_view);
            TextView profile_name_dropTarget = (TextView) dropTargetView.findViewById(R.id.athlete_text_view);
            CircularImageView profile_circle = (CircularImageView) dropped.findViewById(R.id.athlete_image_view);
            TextView profile_name = (TextView) dropped.findViewById(R.id.athlete_text_view);
            profile_circle_dropTarget.setBorderColor(Color.GREEN);
            //profile_circle_dropTarget.setBorderWidth(10);
            profile_circle_dropTarget.setImageDrawable(profile_circle.getDrawable());
            profile_name_dropTarget.setText(profile_name.getText());

            Integer id = dropped.getId();
            dropTargetView.setTag(id);
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            final View view = (View) event.getLocalState();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    //grey out oar
                    view.setAlpha((float) 0.5);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    resetView(view);
                    break;
                case DragEvent.ACTION_DROP:
                    //handle the dragged view being dropped over a target view
                    view.setEnabled(false);
                    //view dragged item is being dropped on
                    final View dropTarget = v;
                    //if an item has already been dropped here, there will be a tag
                    final Object tag = dropTarget.getTag();
                    //view being dragged and dropped
                    final View dropped = (View) view;
                    //update the text in the target view to reflect the data being dropped
                    if (tag!= null )
                    {
                        //ATHELTE ALREADY SET
                        alertUser("Warning!", "Do you want to replace this Athlete?", new Runnable() {
                            @Override
                            public void run() {
                                //YES IN WARNING
                                //the tag is the view id already dropped here
                                int existingID = (Integer) tag;
                                //set the original view visible again
                                View existView = findViewById(existingID);
                                existView.setVisibility(View.VISIBLE);
                                resetView(existView);
                                //this will be triggered if the user decides he wants to replace the athlete
                                setAthlete(dropped, dropTarget);
                            }
                        }, new Runnable() {
                            @Override
                            public void run() {
                                resetView(view);
                            }
                        });
                    } else {
                        //NOTHING HAS BEEN SET YET
                        setAthlete(dropped, dropTarget);
                    }

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

    public void switchToReg(View view){
        Intent i = new Intent(DropActivity.this, AthleteRegistration.class);
        startActivity(i);
    }

}
