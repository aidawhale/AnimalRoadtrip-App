using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class IglooController : MonoBehaviour {

    public GameObject Pingu;
    public Transform iglooIsland;
    public ParticleSystem confetti;
    private GameObject map;

    private void Start() {
        map = GameObject.FindGameObjectWithTag("Map");
    }

    private void OnTriggerEnter(Collider collision) {

        if(collision.gameObject.name == "Pingu") {
            map.SendMessage("OnShapeCollision", 10);
        }
    }

    public void OnPlayConfetti() {
        if (confetti != null) {
            if (confetti.isPlaying) {
                confetti.Stop();
                confetti.time = 0;
            }
            confetti.Play();
        }
    }

    public void OnTouchDetected() {
        Pingu.SendMessage("OnMoveTowards", iglooIsland.position);
    }

}
