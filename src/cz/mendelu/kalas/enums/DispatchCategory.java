package cz.mendelu.kalas.enums;

import static cz.mendelu.kalas.Utils.getRandom;

public enum DispatchCategory {
    VERY_FAST {
        @Override
        public Integer getTime() {
            return getRandom(1,2);
        }
    },
    FAST {
        @Override
        public Integer getTime() {
            return getRandom(3,5);
        }
    },
    SLOW {
        @Override
        public Integer getTime() {
            return getRandom(6,15);
        }
    },
    SLOWEST {
        @Override
        public Integer getTime() {
            return getRandom(16,30);
        }
    };


    public abstract Integer getTime();

}
