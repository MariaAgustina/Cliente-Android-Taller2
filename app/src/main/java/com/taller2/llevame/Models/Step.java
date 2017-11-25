package com.taller2.llevame.Models;
import com.taller2.llevame.Models.TrajectoryLocation;

import java.io.Serializable;

/**
 * Created by amarkosich on 11/25/17.
 */

public class Step implements Serializable {
    public TrajectoryLocation start_location;
    public TrajectoryLocation end_location;
}
