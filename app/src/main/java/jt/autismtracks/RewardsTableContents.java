package jt.autismtracks;

import android.provider.BaseColumns;

/**
 * Created by chrx on 4/20/16.
 */
public class RewardsTableContents {
    public RewardsTableContents() {}

    public static abstract class RewardsEntry implements BaseColumns {
        public static final String TABLE_NAME = "Rewards";
        public static final String COLUMN_NAME_Reward = "reward";
        public static final String COLUMN_NAME_POINTS = "points";
        public static final String COLUMN_NAME_ICON = "icon_src";
    }
}
