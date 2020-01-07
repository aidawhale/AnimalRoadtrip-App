using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ChronoController : MonoBehaviour {

    private float time;
    private Text t;
    private bool count;

    // Start is called before the first frame update
    void Start() {
        t = this.GetComponent<Text>();
        t.text = "";
    }

    // Update is called once per frame
    void Update() {
        if(count) {
            time += Time.deltaTime;
            t.text = ((int)time).ToString();
        }
    }

    public void OnInitChrono() {
        if(t == null) {
            t = this.GetComponent<Text>();
        }
        t.text = "0";
        time = 0;
    }

    public void OnStartChrono() {
        count = true;
    }

    public void OnStopChrono() {
        count = false;
    }
}
