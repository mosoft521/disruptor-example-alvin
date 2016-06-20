package com.gmail.mosoft521.disruptor.example.bizrules;

/**
 * User: mbharadwaj
 * Date: 7/25/11
 */
public class DisruptorHandler implements EventProcessor<BizRuleData> {
    private final BizRuleStep bizRuleStep;
    private volatile long sequence;
    private long bizRuleCounter = 0;

    public DisruptorHandler(final BizRuleStep bizRuleStep) {
        super();
        this.bizRuleStep = bizRuleStep;
    }

    public void reset() {
        bizRuleCounter = 0;
        sequence = 0;
    }

    public long getBizRuleCounter() {
        return bizRuleCounter;
    }

    public void onAvailable(final BizRuleData data) throws Exception {
        switch (bizRuleStep) {
            case VALIDATE:
                data.validate(DiamondPathExample.MOD_VALUE);
                break;

            case SAVE:
                data.save();
                break;

            case NOFITY:
                if (data.isValid() && data.isSaved()) {
                    //sendNotification
                    bizRuleCounter++;
                }
                break;
        }
        sequence++;
    }

    public void onEndOfBatch() throws Exception {
    }

    public long getSequence() {
        return sequence;
    }
}

