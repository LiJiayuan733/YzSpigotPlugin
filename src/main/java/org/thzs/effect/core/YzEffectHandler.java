package org.thzs.effect.core;

public class YzEffectHandler implements Runnable{
    public boolean Run=true;
    @Override
    public void run() {
        while (Run){
            YzEffect effect;
            try{
                effect=YzEffectHandlerThread.instance.hasEffect();
            }catch (Exception e){
                continue;
            }
            if(effect==null){
                try {
                    Thread.sleep(1000/20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }else{
                effect.update(System.currentTimeMillis());
                effect.check();
            }
        }
    }
}
