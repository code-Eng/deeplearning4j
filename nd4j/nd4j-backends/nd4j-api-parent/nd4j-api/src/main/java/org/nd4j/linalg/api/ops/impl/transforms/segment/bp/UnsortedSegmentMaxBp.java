/*******************************************************************************
 * Copyright (c) 2015-2018 Skymind, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * https://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 ******************************************************************************/

package org.nd4j.linalg.api.ops.impl.transforms.segment.bp;

import org.nd4j.autodiff.samediff.SDVariable;
import org.nd4j.autodiff.samediff.SameDiff;
import org.nd4j.linalg.api.ops.DynamicCustomOp;

/**
 * Unsorted segment max backprop operation
 *
 * @author Alex Black
 */
public class UnsortedSegmentMaxBp extends DynamicCustomOp {

    private int numSegments;

    public UnsortedSegmentMaxBp(SameDiff sameDiff, SDVariable data, SDVariable segmentIds, SDVariable gradient, int numSegments) {
        super(null, sameDiff,  new SDVariable[] {data, segmentIds, gradient}, false);
        this.numSegments = numSegments;
        addIArgument(numSegments);
    }

    public UnsortedSegmentMaxBp(){ }

    @Override
    public String opName(){
        return "unsorted_segment_max_bp";
    }

}
