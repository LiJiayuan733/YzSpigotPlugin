package org.thzs.effect.core;

public class YzEffectHandler implements Runnable{
    public boolean Run=true;
    public int Channel=0;
    public YzEffectHandler(int Channel){
        this.Channel=Channel;
    }
    @Override
    public void run() {
        while (Run){
            YzEffect effect;
            try{
                effect=YzEffectHandlerThread.instance.hasEffect(Channel);
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
                try {
                    effect.update(System.currentTimeMillis());
                    effect.check();
                } catch (Exception e){
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
