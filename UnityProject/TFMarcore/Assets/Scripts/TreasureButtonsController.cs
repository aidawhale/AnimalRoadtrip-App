using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class TreasureButtonsController : MonoBehaviour {

    public GameObject moveButton;
    public GameObject doneButton;
    public GameObject scalePanel;
    public GameObject rotatePanel;

    private GameObject map = null;

    public void OnMoveSelect() {
        moveButton.SetActive(false);
        doneButton.SetActive(true);
        scalePanel.SetActive(true);
        rotatePanel.SetActive(true);
    }

    public void OnDoneSelect() {
        moveButton.SetActive(true);
        doneButton.SetActive(false);
        scalePanel.SetActive(false);
        rotatePanel.SetActive(false);
    }

    public void OnScaleSliderChange(Slider slider) {
        if(map == null) {
            map = GameObject.FindGameObjectWithTag("Map");
        }

        map.transform.localScale = new Vector3(slider.value, slider.value, slider.value);
    }

    public void OnRotateSliderChange(Slider slider) {
        if (map == null) {
            map = GameObject.FindGameObjectWithTag("Map");
        }

        map.transform.Rotate(Vector3.up, slider.value);
    }

}
