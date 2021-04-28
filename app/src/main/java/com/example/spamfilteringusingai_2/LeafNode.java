package com.example.spamfilteringusingai_2;

import java.util.List;

public class LeafNode extends TreeNode {

    private double[] labelProbDist;

    public LeafNode(int numLabels, List<Integer> labels) {
        super();
        labelProbDist = new double[numLabels];
        int size = labels.size();
        for (Integer label : labels) {
            labelProbDist[label] += 1.0 / size;
        }
    }

    public double[] getLabelProbDist() {
        return labelProbDist;
    }
}
