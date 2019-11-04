package mm.com.fairwaytech.Helper;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import mm.com.fairwaytech.Adapter.AdminAdapter;

public class AdminRecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private AdminRecyclerItemTouchHelperListener listener;


    public AdminRecyclerItemTouchHelper(int dragDirs, int swipeDirs, AdminRecyclerItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }
                        // ------------------------------------------------------------------------------------------------------
                        // getDefaultUIUtil() will be used by ItemTouchHelper to detect whenever there is UI change on the view.
                        // We use this function to keep the background view in a static position and move the foreground view.
                        // ------------------------------------------------------------------------------------------------------
    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null){
            final View foregroundView = ((AdminAdapter.ViewHolder) viewHolder).viewForeground;
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }
                        // ------------------------------------------------------------------------------------------------------
                        //  In onChildDrawOver() the x-position of the foreground view is changed while user is swiping the view.
                        // ------------------------------------------------------------------------------------------------------
    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((AdminAdapter.ViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((AdminAdapter.ViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((AdminAdapter.ViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState,isCurrentlyActive);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }
                        // ------------------------------------------------------------------------------------------------------
                        // RecyclerItemTouchHelperListener interface used to send the callback to implementing activity.
                        // Here the listener will be triggered in NavigationActivity once the swipe is done.
                        // ------------------------------------------------------------------------------------------------------
    public interface AdminRecyclerItemTouchHelperListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
