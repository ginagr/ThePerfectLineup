package edu.wpi.www.theperfectlineup;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ClipData;
import android.view.Display;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import com.makeramen.roundedimageview.RoundedImageView;


public class DropActivity extends AppCompatActivity {

    private User mUser = new User("Rowing");//TODO this is a mock user with rowing.

    private Athlete[] mAthletesLeft = new Athlete[]{
        new Athlete("Athlete 1", 21),
                new Athlete("Athlete 2", 24),
                new Athlete("Athlete 3", 21),
                new Athlete("Athlete 4", 27),
                new Athlete("Athlete 5", 18),
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
        addAthletes(layout1, mAthletesLeft,width);
        addBoat(width, height, 8);//TODO will have to get the boatSize from the chosen boat
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


    public void addAthletes(ViewGroup view, Athlete[] athleteArr, int width) {
        view.setScrollContainer(true);
        for (int i = 0; i < athleteArr.length; i++) {
            RoundedImageView[] viewAthletes = new RoundedImageView[athleteArr.length];  //TODO add textView to portrower obj
            RoundedImageView dropTarget = new RoundedImageView(this);
            dropTarget.setScaleType(ImageView.ScaleType.CENTER_CROP);
            dropTarget.setCornerRadius((float) 10);
            dropTarget.setBorderWidth((float) 2);
            dropTarget.setBorderColor(Color.DKGRAY);
            dropTarget.mutateBackground(true);
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.profile_image);
            dropTarget.setImageDrawable(drawable);
            dropTarget.setOval(true);
            viewAthletes[i]= dropTarget;
            viewAthletes[i].setId(989023490 + i);//needed some unique tag for athletes.
            viewAthletes[i].setOnTouchListener(new ChoiceTouchListener());
            view.addView(viewAthletes[i]);
        }
    }



    public void addBoat(int width,int height, int boatSize)
    {
        LinearLayout layout = (LinearLayout) findViewById(R.id.lineupLinearLayout);


        layout.setScrollContainer(true);
        int diameter = ((height-(height/100*14))/(boatSize+1));//14% higher than the lowest point to avoid statusbar
        LinearLayout.LayoutParams dynamicParams = new LinearLayout.LayoutParams(diameter,diameter);
        //Setting all the properties of the circle



        //Setting up the GridView

        //Setting up the RoundedImageView

        //Setting up the TextView
//        background.setLayoutParams(dynamicParams);
//        athleteImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        athleteImageView.setCornerRadius((float) 10);
//        athleteImageView.setBorderWidth((float) 2);
//        athleteImageView.setBorderColor(Color.DKGRAY);
//        athleteImageView.mutateBackground(true);
//        athleteImageView.setBackgroundColor(Color.LTGRAY);
//        athleteImageView.setOval(true);
//        athleteImageView.setOnDragListener(new ChoiceDragListener());;
//        athleteTextView.setText("Test");
//        background.addView(athleteImageView);
//        background.addView(athleteTextView);

        for (int i = boatSize; i > 0; i--)
        {
            LayoutInflater inflater = LayoutInflater.from(this);
            LinearLayout profile = (LinearLayout)inflater.inflate(R.layout.bubble_profile, null);
            RoundedImageView profile_circle = (RoundedImageView) profile.findViewById(R.id.athlete_image_view);
            TextView profile_text = (TextView) profile.findViewById (R.id.athlete_text_view);
            profile_text.setText("Athlete"+i);
            profile_circle.setLayoutParams(dynamicParams);
            profile_circle.setOnDragListener(new ChoiceDragListener());
//            background = new RoundedImageView(this);
//            background.setLayoutParams(dynamicParams);
//            background.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            background.setCornerRadius((float) 10);
//            background.setBorderWidth((float) 2);
//            background.setBorderColor(Color.DKGRAY);
//            background.mutateBackground(true);
//            background.setBackgroundColor(Color.LTGRAY);
//            background.setOval(true);
//            background.setOnDragListener(new ChoiceDragListener());
//            layout.addView(background);
//            background = new TextView(this);
//
//               // name.setText("Rower" + i);//TODO should eventually reference the sport
//                background.setBackgroundResource(R.drawable.profile_image);
//                background.setLayoutParams(dynamicParams);
//                background.setOnDragListener(new ChoiceDragListener());
//             //   layout.addView(name);
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
//            dropTarget.setText(dropped.getText());
//            dropTarget.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
//            //  String temp = dropped.getText().toString();
//            // temp = temp.charAt(temp.length()-1);
//            //  int temp2 = Integer.parseInt(temp);
//            if(dropped.getMaxLines() == 2){
//                dropTarget.setBackgroundResource(R.drawable.portrower);
//            }
//            else {
//                dropTarget.setBackgroundResource(R.drawable.starboardrower);
//            }

            RoundedImageView profile_circle = (RoundedImageView) dropTargetView.findViewById (R.id.athlete_image_view);
            //RoundedImageView dropTarget = (RoundedImageView) dropTargetView;
            Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.profile_image, null);
            profile_circle.setImageDrawable(d);

//            dropTarget.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            dropTarget.setCornerRadius((float) 10);
//            dropTarget.setBorderWidth((float) 2);
            profile_circle.setBorderColor(Color.GREEN);

//            dropTarget.mutateBackground(true);
//            dropTarget.setOval(true);

            //Display display = getWindowManager().getDefaultDisplay();
            //int width = display.getWidth();
           // dropTarget.setWidth((int) (width / 3 * .85));
           // dropTarget.setHeight((int) (width / 3 * .85) / 2);
            //make it bold to highlight the fact that an item has been dropped
           // dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
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

}
