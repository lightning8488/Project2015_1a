//
//　　履修教室：大演習室
//　　学籍番号：1572090
//　　氏　　名：目黒翔大
//
//////////////////////////////////////////////////////////////////
//
//  プログラミング基礎２（Java)　プロジェクト課題用ファイル
//
//　　概要：
//　　　１．マウスの瞬間動作、移動動作に対するリスナー
//　　　２．複数の画像ファイル（配列として）
//　　　３．一部で、画面を回転させることで画像を傾けて描画した例を入れてある
//

import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;

public class Project2015_1a extends Applet implements Runnable
{
    int a, b, h, n, i = 0, j = 0, k = 0, l = 0, radio = 40, rad, stop = 0, tv = 0, t, power = 0, day = 1, sky = 0, select;
    Thread th;
    //　ここはフィールドです　//
    // プログラム内で使う変数や、オブジェクトの入れものを用意する。
    int Mx = 0, My = 0;  // マウスのｘ座標の記録用変数 Mx,　ｙ座標の記録用変数 My
    Graphics g;          // アプレットに組み込まれている標準グラフィクス描画対象 g
    Graphics2D g2;       // 高度なグラフィクス機能（Graphics2D）を使う場合の描画対象　g2　
    Applet app;          // アプレットである自分を、リスナーオブジェクトから呼び出せるようにするための入れ物　app
    Image img[];         // 画像ファイルから読み込む複数の画像を入れるためのオブジェクト （今回は配列として用意）
    AudioClip ac[];      // 音声ファイルを入れるためのオブジェクト（今回は配列として用意）
    Button bt1, bt2, bt3, bt4, bt5, bt6, bt7;          // ボタン部品の入れ物 bt1 を用意する。

    //　アプレット起動時の初期化処理
    @Override
    public void init()
    {
        setLayout(null);  //ボタンの配置の自由化
        
        //　アプレット自身（this） を、どこからでも呼び出せるように app に代入しておく。
        app = this;
        app.setSize(600, 600);  //初期アプレットサイズを600x600に設定

        // このアプレットが持つ描画面を受け取り、gとして呼び出せるようにする。
        g = app.getGraphics();
        g2 = (Graphics2D) g;  // Graphics2Dの機能を使う場合は、g2として呼び出せるようにする。

        app.setBackground(new Color(250, 250, 250));  //背景を薄い灰色に着色

        // 用意しておいた5枚の画像を順番に画像用配列img[]に読み込み、プログラム中で利用できるようにする。
        //　ファイル名は　g0.jpg～g4.jpg　であるが、for文で数字部分を変えながらファイル名を指定している。
        img = new Image[5];
        for (int i = 0; i < 5; i++) 
        {
            img[i] = app.getImage(app.getCodeBase(), "images/g" + i + ".jpg");
        }
        
        // 用意しておいた4つの音源を順番に音楽用配列ac[]に読み込み、プログラム中で利用できるようにする。
        //　ファイル名は　m0.wav～m3.wav　であるが、for文で数字部分を変えながらファイル名を指定している。
        ac = new AudioClip[5];
        for (int i = 0; i < 4; i++) 
        {
            ac[i] = app.getAudioClip(app.getCodeBase(), "sounds/m" + i + ".wav");
        }
        ac[4] = app.getAudioClip(app.getCodeBase(), "sounds/bell.wav");

        //「再生」ボタンを(96, 550)の位置に配置
        bt1 = new Button("再生");
        bt1.setBounds(96, 550, 60, 20);
        add(bt1);

        //「次」ボタン（ラジカセ用）を(145, 525)の位置に配置
        bt2 = new Button("次");
        bt2.setBounds(145, 525, 20, 20);
        add(bt2);

        //「前」ボタン（ラジカセ用）を(87, 525)の位置に配置
        bt3 = new Button("前");
        bt3.setBounds(87, 525, 20, 20);
        add(bt3);

        //「アンテナ」ボタンを(96, 500)の位置に配置
        bt4 = new Button("アンテナ");
        bt4.setBounds(96, 500, 60, 20);
        add(bt4);

        //「ON / OFF」ボタンを(451, 517)の位置に配置
        bt5 = new Button("ON / OFF");
        bt5.setBounds(451, 517, 60, 20);
        add(bt5);

        //「次」ボタン（テレビ用）を(500, 545)の位置に配置
        bt6 = new Button("次");
        bt6.setBounds(500, 545, 60, 20);
        add(bt6);

        //「前」ボタン（テレビ用）を(402, 545)の位置に配置
        bt7 = new Button("前");
        bt7.setBounds(402, 545, 60, 20);
        add(bt7);

        //「再生」を押すと、音楽が流れる＆♪が現れる・止まる＆♪が消える
        bt1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                stop++;  //stopでボタンが押された回数をカウント
                rad = radio;
                radio = radio % 4;  //radioの数値を4で割った余りによって流れる音楽が変動
                if(stop % 2 != 0)  //stopが奇数のとき、音楽が流れる＆♪が描画される
                { 
                    ac[radio].loop();
                    Font f1;
                    f1 = new Font("TimesRoman", Font.ROMAN_BASELINE, 12);
                    g2.setFont(f1);
                    g2.setColor(new Color(255, 0, 0));
                    g2.drawString("♪", 200, 280);
                    g2.drawString("♪", 180, 260);
                    g2.drawString("♪", 220, 260);
                    g2.drawString("♪", 190, 280);
                    g2.drawString("♪", 170, 280);
                    g2.drawString("♪", 230, 250);
                }
                else  //stopが偶数のとき、音楽が止まる＆♪が消える
                {
                    if(radio < 0)
                    {
                        n = radio;
                        n = -n;
                        ac[n].stop();
                    }
                    else
                    {
                        ac[radio].stop();
                    }
                    g2.setColor(new Color(250, 250, 250));
                    g2.fillRect(169, 239, 72, 42);  //♪が描かれていた部分に背景と同色の長方形を描画
                }
                radio = rad;
            }
        });
        
        //「次」ボタン（ラジカセ用）を押すと、次の曲に変わる
        //m0, m1, m2, m3, m1...という順番で曲目はループする
        bt2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                if(stop % 2 != 0)  //「再生」ボタンによって音楽が再生されていたときのみ次の曲に変わる
                {
                    radio++;
                    rad = radio;
                    radio = radio % 4;
                    if(radio == 0)  //m0に戻ったとき、m3を停止
                    {
                        n = 3;
                        ac[n].stop();
                    }
                    else  //次の曲に移ったとき、再生されていた曲を停止
                    {
                        ac[radio - 1].stop();
                    }
                    ac[radio].loop();
                    radio = rad;
                }
                else
                {
                }
            }
        });

        //「前」ボタン（ラジカセ用）を押すと、前の曲に変わる
        //m3, m2, m1, m0, m3...という順番で曲目はループする
        bt3.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                if(stop % 2 != 0)  //「再生」ボタンによって音楽が再生されていたときのみ前の曲に変わる
                {
                    radio--;
                    rad = radio;
                    radio = radio % 4;
                    if(radio == 3)  //m3に戻ったとき、m0を停止
                    {
                        ac[0].stop();
                    }
                    else  //前の曲に移ったとき、再生されていた曲を停止
                    {
                        ac[radio + 1].stop();
                    }

                    if(radio < 0)  //radioが負の数のとき、正の数に直す
                                   //負の数は-1, -2, -3, -4と変化するから、ここで調節する
                    {
                        radio = -radio;
                        if(radio == 1)
                        {
                            radio = 3;
                            ac[radio].loop();
                        }
                        else if(radio == 3)
                        {
                            radio = 1;
                            ac[radio].loop();
                        }
                        else
                        {
                            ac[radio].loop();
                        }
                    }
                    else
                    {
                        ac[radio].loop();
                    }
                
                    radio = rad;
                }
                else
                {
                }
            }
        });

        //「アンテナ」ボタンを押すとアンテナが立つ・寝る
        bt4.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                if(j % 2 == 0)
                {
                    g2.drawLine(85, 298, 160, 250);
                    g2.setColor(new Color(250, 250, 250));
                    g2.drawLine(92, 298, 175, 298);  //アンテナが描かれていた位置に背景と同色の直線を描画
                    g2.setColor(new Color(0, 0, 0));
                }
                else
                {
                    g2.drawLine(85, 298, 175, 298);
                    g2.setColor(new Color(250, 250, 250));
                    g2.drawLine(93, 293, 168, 245);  //アンテナが描かれていた位置に背景と同色の直線を描画
                    g2.drawLine(92, 293, 167, 245);
                    g2.setColor(new Color(0, 0, 0));
                }
                j++;
            }
        });

        //「ON / OFF」ボタンを押すと、テレビが点く・消える（若干バグる）
        bt5.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                power++;
                if(power % 2 != 0)
                {
                    t = tv;
                    tv = tv % 5;  //tvの数値を5で割った余りによって描画される画像が変動
                    if(tv < 0)  //tvが負の数のとき、正の数に直してから描画
                    {
                        tv = -tv;
                        g2.drawImage(img[tv], 403, 302, 154, 74, app);
                    }
                    else
                    {
                        g2.drawImage(img[tv], 403, 302, 154, 74, app);
                    }
                    tv = t;
                }
                else
                {
                    g2.fillRect(400, 299, 160, 80);  //画像が表示されていた部分に黒色の長方形を描画
                }
            }
        });

        //「次」ボタン（テレビ用）を押すと、次のチャンネルに変わる（若干バグる）
        bt6.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                if(power % 2 != 0)  //「ON / OFF」ボタンによって画像が描画されていたときのみ次のチャンネルに変わる
                {
                    tv++;
                    t = tv;
                    tv = tv % 5;
                    if(tv < 0)  //tvが負の数のとき、正の数に直してから描画
                    {
                        tv = -tv;
                        g2.drawImage(img[tv], 403, 302, 154, 74, app);
                    }
                    else
                    {
                        g2.drawImage(img[tv], 403, 302, 154, 74, app);
                    }
                    tv = t;
                }
                else
                {
                }
            }
        });

        //「前」ボタン（テレビ用）を押すと、前のチャンネルに変わる
        bt7.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                if(power % 2 != 0)  //「ON / OFF」ボタンによって画像が描画されていたときのみ前のチャンネルに変わる
                {
                    tv--;
                    t = tv;
                    tv = tv % 5;
                    if(tv < 0)  //tvが負の数のとき、正の数に直す
                                //負の数は-1, -2, -3, -4, -5と変化するから、ここで調節する
                    {
                        tv = -tv;
                        if(tv == 1)
                        {
                            tv = 4;
                            g2.drawImage(img[tv], 403, 302, 154, 74, app);
                        }
                        else if(tv == 2)
                        {
                            tv = 3;
                            g2.drawImage(img[tv], 403, 302, 154, 74, app);
                        }
                        else if(tv == 3)
                        {
                            tv = 2;
                            g2.drawImage(img[tv], 403, 302, 154, 74, app);
                        }
                        else if(tv == 4)
                        {
                            tv = 1;
                            g2.drawImage(img[tv], 403, 302, 154, 74, app);
                        }
                        else
                        {
                            g2.drawImage(img[tv], 403, 302, 154, 74, app);
                        }
                    }
                    else
                    {
                        g2.drawImage(img[tv], 403, 302, 154, 74, app);
                    }
                    tv = t;
                }
                else
                {
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////
        // (1)マウスの瞬間動作に反応する「イベントリスナー」MouseListenerを登録して使う
        ///////////////////////////////////////////////////////////////////////////
        this.addMouseListener(
                new MouseAdapter()
                {
                    //マウスがクリックされたときの処理　なし
                    @Override
                    public void mouseClicked(MouseEvent e)
                    {
                    }

                    //マウスカーソルが画面内に入ったとき「ガチャ」と表示される
                    @Override
                    public void mouseEntered(MouseEvent e)
                    {
                        g.setColor(new Color(250, 250, 250));
                        g2.fillRect(0, 280, 50, 50);
                        g.setColor(Color.black);
                        g2.drawString("ガチャ", 0, 300);
                    }

                    //マウスカーソルが画面から出たとき「バタン」と表示される
                    @Override
                    public void mouseExited(MouseEvent e)
                    {
                        g.setColor(new Color(250, 250, 250));
                        g2.fillRect(0, 280, 50, 50);
                        g.setColor(Color.black);
                        g2.drawString("バタン", 0, 300);
                    }

                    //マウスボタンが押されたときの処理　なし
                    @Override
                    public void mousePressed(MouseEvent e)
                    {
                    }

                    //マウスボタンを放したときの処理　なし
                    @Override
                    public void mouseReleased(MouseEvent e)
                    {
                    }
                });

        ///////////////////////////////////////////////////////////////////////////
        // (2)マウスの連続動作に反応する「イベントリスナー」MouseMotionListenerを登録して使う
        ///////////////////////////////////////////////////////////////////////////
        this.addMouseMotionListener(
                new MouseMotionAdapter()
                {
                    //ドラッグしたときの処理　なし
                    @Override
                    public void mouseDragged(MouseEvent e)
                    {
                    }

                    //マウスを動かしたとき、時計が動く
                    @Override
                    public void mouseMoved(MouseEvent e)
                    {
                        //針の描画間隔の調節
                        try
                        {
                            Thread.sleep(100);
                        }
                        catch(InterruptedException m)
                        {
                            stop();
                        }
                        
                        //座標軸の回転を利用し、分針を描画
                        g2.setColor(Color.black);
                        g2.translate(92, 80);
                        g2.rotate(Math.toRadians(k));
                        g2.fillRect(-1, -40, 3, 40);
                        g2.rotate(Math.toRadians(-k));
                        g2.rotate(Math.toRadians(k-10));  //直前に描かれた分針を白で塗りつぶす
                        g2.setColor(Color.white);
                        g2.fillRect(-1, -40, 3, 40);
                        g2.rotate(Math.toRadians(-k+10));
                        g2.translate(-92, -80);
                        
                        //座標軸の回転を利用し、時針を描画
                        g2.translate(92, 80);
                        g2.rotate(Math.toRadians(l));
                        g2.setColor(Color.black);
                        g2.fillRect(0, 5, 3, 20);
                        g2.rotate(Math.toRadians(-l));
                        g2.rotate(Math.toRadians(l-5));  //直前に描かれた時針を白で塗りつぶす
                        g2.setColor(Color.white);
                        g2.fillRect(0, 5, 3, 20);
                        g2.rotate(Math.toRadians(-l+5));
                        g2.translate(-92, -80);
                        
                        g2.setColor(Color.black);  //時計の中心点を描画
                        g2.fillOval(90, 78, 5, 5);

                        //分針が1回転した回数によって、窓の外の風景を変える
                        if((l % 360 == 0) && (sky % 2 == 0))  //1回転した回数が偶数のとき、夜
                        {
                            g2.setColor(new Color(0, 0, 120));
                            g2.fillRect(385, 55, 92, 221);
                            g2.fillRect(483, 55, 92, 221);
                            g2.setColor(Color.yellow);
                            for(h = 0 ; h < 5 ; h++)  //乱数によって描画する星の数を変更（最大５　最少１）
                            {
                                select = (int) (Math.random() * 10);
                                if((select > 0) && (select < 11))
                                {
                                    g.drawString("★", 430, 80);
                                    if((select > 1) && (select < 10))
                                    {
                                        g.drawString("★", 500, 90);
                                        if((select > 2) && (select < 9))
                                        {
                                            g.drawString("★", 400, 100);
                                            if((select > 3) && (select < 8))
                                            {
                                                g.drawString("★", 540, 80);
                                                if((select > 5) && (select < 7))
                                                {
                                                    g.drawString("★", 450, 115);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            sky = sky + 1;
                        }
                        else if((l % 360 == 0) && (sky % 2 == 1))  //1回転した回数が奇数のとき、朝
                        {
                            g2.setColor(new Color(220, 255, 255));
                            g2.fillRect(385, 55, 92, 221);
                            g2.fillRect(483, 55, 92, 221);
                            AlphaComposite a = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);  //透明度を変えながら太陽を描画
                            g2.setComposite(a);
                            g2.setColor(new Color(255, 200, 0));
                            g2.fillOval(517, 58, 50, 50);
                            AlphaComposite b = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
                            g2.setComposite(b);
                            g2.setColor(new Color(255, 200, 0));
                            g2.fillOval(522, 63, 40, 40);
                            AlphaComposite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
                            g2.setComposite(c);
                            g2.setColor(new Color(255, 150, 0));
                            g2.fillOval(527, 68, 30, 30);
                            AlphaComposite d = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
                            g2.setComposite(d);
                            g2.setColor(new Color(255, 150, 0));
                            g2.fillOval(532, 73, 20, 20);
                            g2.translate(382, 52);
                            g2.rotate(Math.toRadians(-40));  //窓のつやを描画
                            g2.setColor(Color.white);
                            g2.fillRect(-5, 18, 18, 115);
                            g2.fillRect(-78, 100, 12, 120);
                            g2.fillRect(-58, 120, 3, 90);
                            g2.fillRect(-5, 169, 18, 115);
                            g2.fillRect(45, 169, 10, 60);
                            g2.rotate(Math.toRadians(40));
                            g2.translate(-382, -52);
                            AlphaComposite f = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
                            g2.setComposite(f);
                            g2.setColor(new Color(255, 150, 0));
                            g2.fillOval(537, 78, 10, 10);
                            
                            
                            sky = sky + 1;

                            //朝になったとき、カレンダーの日付を一日進ませる
                            day = day + 1;
                            Font f2;
                            f2 = new Font("TimesRoman", Font.BOLD, 80);
                            g2.setFont(f2);
                            g2.setColor(Color.white);
                            g2.fillRect(200, 50, 120, 130);
                            g2.setColor(Color.black);
                            g2.drawRect(200, 50, 120, 15);
                            g2.fillOval(260, 52, 5, 5);
                            g2.drawString(day +"", 240, 150);
                            f2 = new Font("TimesRoman", Font.BOLD, 12);
                            g2.setFont(f2);
                            if(day == 31)  //日付が31日になったら、dayをリセット
                            {
                                day = 0;
                            }
                            else
                            {
                            }
                        }
                        else
                        {
                        }
                        
                        k = k + 10;
                        l = l + 5;
                    }
                });

        th = new Thread(this);
        th.start();
    }

    //----------------------------------------------------------------------------------
    // 背景として、最初に描いておきたい画像や図形は、paintメソッドで描いておくのが良いです。
    //　イベントの中からpaintメソッドを呼ぶには、repaint();　という命令を書けば、
    //　paintメソッドを呼ぶことができます。
    //　ただし、そのときには、それまでの画面は一度消されてから、描き直しが行われます。
    @Override
    public void paint(Graphics g)
    {
        Font f;
        Graphics2D g2d = (Graphics2D) g; //受け取ったgをGraphics2D用に変換します。

        //床部分の描画
        g2d.drawLine(0, 449, 600, 449);
        g2d.setColor(new Color(230, 230, 230));
        g2d.fillRect(0, 450, 600, 150);

        //窓の描画
        g2d.setColor(new Color(100, 100, 100));
        g2d.drawRect(382, 52, 97, 226);
        g2d.drawRect(383, 53, 95, 224);
        g2d.drawRect(384, 54, 93, 222);
        g2d.drawRect(480, 52, 97, 226);
        g2d.drawRect(481, 53, 95, 224);
        g2d.drawRect(482, 54, 93, 222);
        g2d.setColor(Color.black);
        g2d.drawRect(378, 48, 204, 234);
        g2d.drawRect(379, 49, 202, 232);
        g2d.drawRect(380, 50, 200, 230);
        g2d.drawRect(381, 51, 198, 228);
        g2d.drawRect(382, 52, 196, 226);
        g2d.drawLine(479, 52, 479, 280);

        //時計の描画（針以外）
        g2d.setColor(Color.BLUE);
        g2d.drawOval(40, 30, 100, 100);
        g2d.drawOval(40, 30, 101, 101);
        g2d.drawOval(39, 29, 102, 102);
        g2d.drawOval(39, 29, 103, 103);
        g2d.drawOval(39, 29, 104, 104);
        g2d.drawOval(38, 28, 104, 104);
        g2d.setColor(Color.white);
        g2d.fillOval(41, 31, 100, 100);
        g2d.setColor(Color.black);
        g2d.drawLine(91, 30, 91, 47);
        g2d.drawLine(92, 30, 92, 47);
        g2d.drawLine(91, 114, 91, 131);
        g2d.drawLine(92, 114, 92, 131);
        g2d.drawLine(122, 79, 139, 79);
        g2d.drawLine(122, 80, 139, 80);
        g2d.drawLine(41, 79, 58, 79);
        g2d.drawLine(41, 80, 58, 80);
        g2d.drawLine(120, 62, 132, 53);
        g2d.drawLine(120, 63, 132, 54);
        g2d.drawLine(108, 51, 115, 37);
        g2d.drawLine(109, 51, 116, 37);
        g2d.drawLine(48, 105, 60, 96);
        g2d.drawLine(48, 106, 60, 97);
        g2d.drawLine(67, 124, 74, 110);
        g2d.drawLine(68, 124, 75, 110);
        g2d.drawLine(116, 124, 109, 110);
        g2d.drawLine(117, 124, 110, 110);
        g2d.drawLine(132, 105, 120, 96);
        g2d.drawLine(132, 106, 120, 97);
        g2d.drawLine(60, 62, 48, 53);
        g2d.drawLine(60, 63, 48, 54);
        g2d.drawLine(74, 51, 67, 37);
        g2d.drawLine(75, 51, 68, 37);
        g2d.fillOval(90, 78, 5, 5);

        //カレンダーの描画（１日のみ）
        f = new Font("TimesRoman", Font.BOLD, 80);
        g2d.setFont(f);
        g2d.setColor(Color.white);
        g2d.fillRect(200, 50, 120, 130);
        g2d.setColor(Color.black);
        g2d.drawRect(200, 50, 120, 15);
        g2d.fillOval(260, 52, 5, 5);
        g2d.drawString("1", 240, 150);

        //テーブルの描画
        g2d.setColor(new Color(70, 0, 0));
        g2d.fillOval(40, 365, 20, 130);
        g2d.fillOval(190, 365, 20, 130);
        g2d.setColor(new Color(85, 0, 0));
        g2d.fillOval(41, 487, 18, 18);
        g2d.fillOval(191, 487, 18, 18);
        g2d.setColor(new Color(50, 0, 0));
        g2d.fillOval(75, 365, 20, 95);
        g2d.fillOval(155, 365, 20, 95);
        g2d.fillOval(76, 455, 18, 18);
        g2d.fillOval(156, 455, 18, 18);
        g2d.setColor(new Color(100, 0, 0));
        g2d.fillOval(0, 350, 250, 50);

        //ラジカセの描画
        g2d.setColor(new Color(235, 235, 235));
        g2d.fillRect(62, 303, 125, 68);
        g2d.setColor(Color.black);
        g2d.drawRect(62, 303, 125, 68);
        g2d.drawRect(79, 307, 12, 5);
        g2d.drawRect(96, 307, 10, 5);
        g2d.drawRect(111, 307, 12, 5);
        g2d.drawRect(128, 307, 10, 5);
        g2d.drawRect(143, 307, 12, 5);
        g2d.drawRect(160, 307, 10, 5);
        g2d.fillRect(87, 365, 73, 4);
        g2d.fillRect(74, 294, 18, 9);
        g2d.drawLine(85, 298, 175, 298);
        g2d.drawOval(66, 314, 48, 48);
        g2d.drawOval(135, 314, 48, 48);
        AlphaComposite a = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f);
        g2d.setComposite(a);
        g2d.setColor(new Color(0, 0, 0));
        g2d.fillOval(66, 314, 48, 48);
        g2d.fillOval(135, 314, 48, 48);
        AlphaComposite b = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f);
        g2d.setComposite(b);
        g2d.drawOval(76, 324, 28, 28);
        g2d.drawOval(77, 325, 26, 26);
        g2d.drawOval(145, 324, 28, 28);
        g2d.drawOval(146, 325, 26, 26);
        AlphaComposite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
        g2d.setComposite(c);
        g2d.fillOval(85, 333, 10, 10);
        g2d.fillOval(155, 333, 10, 10);

        //ラジカセの操作盤の描画
        AlphaComposite d = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
        g2d.setComposite(d);
        g2d.fillOval(75, 485, 100, 100);
        g2d.fillRect(395, 510, 170, 60);
        g.setColor(Color.yellow);
        f = new Font("TimesRoman", Font.ROMAN_BASELINE, 12);
        g2d.setFont(f);
        g.drawString("Radio", 110, 540);
        g.drawString("TV", 473, 560);

        //テレビ＆テレビ台の描画
        AlphaComposite e = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
        g2d.setComposite(e);
        g.setColor(new Color(20, 20, 20));
        g2d.fillRect(380, 390, 200, 80);
        g2d.fillRect(381, 392, 198, 79);
        g2d.fillRect(382, 394, 196, 78);
        g2d.fillRect(383, 396, 194, 77);
        g2d.fillRect(384, 398, 192, 76);
        g2d.fillRect(385, 400, 190, 75);
        g2d.fillRect(386, 402, 188, 74);
        g2d.fillRect(387, 404, 186, 73);
        g2d.fillRect(388, 406, 184, 72);
        g2d.fillRect(389, 408, 182, 71);
        g2d.fillRect(390, 410, 180, 70);
        g2d.fillRect(391, 412, 178, 69);
        g2d.fillRect(392, 414, 176, 68);
        g2d.fillRect(393, 416, 174, 67);
        g2d.fillRect(394, 418, 172, 66);
        g2d.fillRect(395, 420, 170, 65);
        g2d.fillRect(396, 422, 168, 64);
        g2d.fillRect(397, 424, 166, 63);
        g2d.fillRect(398, 426, 164, 62);
        g2d.fillRect(399, 428, 162, 61);
        g2d.fillRect(400, 430, 160, 60);
        g2d.fillRect(401, 432, 158, 59);
        g2d.fillRect(402, 434, 156, 58);
        g2d.fillRect(403, 436, 154, 57);
        g2d.fillRect(404, 438, 152, 56);
        g2d.fillRect(405, 440, 150, 55);
        g2d.fillRect(406, 442, 148, 54);
        g.setColor(new Color(100, 100, 100));
        g2d.drawLine(380, 390, 406, 416);
        g2d.drawLine(554, 416, 580, 390);
        g2d.drawLine(406, 416, 554, 416);
        g2d.drawLine(406, 416, 406, 495);
        g2d.drawLine(554, 416, 554, 495);
        g2d.fillRect(435, 394, 95, 15);
        g.setColor(new Color(55, 55, 55));
        g2d.fillRect(465, 377, 35, 25);
        g.setColor(new Color(100, 100, 100));
        g2d.fillRect(395, 293, 170, 90);
        g.setColor(Color.black);
        g2d.drawRect(395, 293, 170, 90);
        g2d.fillRect(400, 299, 160, 80);
        g2d.setColor(Color.white);
        f = new Font("TimesRoman", Font.BOLD, 10);
        g2d.setFont(f);
        g2d.drawString("プロジェクト課題 in 2015", 417, 457);
    }

    @Override
    public void run()
    {
        while (true)
        {
            ac[4].play();
            try
            {
                Thread.sleep(10000);
            }
            catch (InterruptedException e)
            {
            }
        }
    }
}
