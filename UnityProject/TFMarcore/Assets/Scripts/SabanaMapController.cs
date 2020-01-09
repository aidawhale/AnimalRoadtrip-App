using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SabanaMapController : MapController {

    public ParticleSystem confetti;

    private GameObject chrono;

    // Start is called before the first frame update
    void Start() {
        chrono = GameObject.Find("ChronoText");

        SetScene();
    }

    public override void SetScene() {
        chrono.SendMessage("OnInitChrono");

        BroadcastMessage("OnResetPosition");

        chrono.SendMessage("OnStartChrono");
    }

    public override void MyVictory() {
        GameObject.Find("HomePanel").SendMessage("OnShowBackgroundPanel");

        if (confetti != null) {
            if (confetti.isPlaying) {
                confetti.Stop();
                confetti.time = 0;
            }
            confetti.Play();
        }

        chrono.SendMessage("OnStopChrono");
    }

    public override void RestartGame() {
        Handheld.Vibrate();

        SetScene();
    }

    public override void OnUpdateItemPosition() {
        BroadcastMessage("OnUpdatePosition");
    }

    public void OnObjectCollision(int obj) {
        // Object can be animal or map interactable element

        if (CheckObjectOrder(obj)) {
            if (obj == 10) {
                MyVictory();
            }
            return;
        }
        RestartGame();
    }

    private bool CheckObjectOrder(int position) {

        return false;
    }
}
