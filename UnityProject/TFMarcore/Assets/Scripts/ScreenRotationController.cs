using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ScreenRotationController : MonoBehaviour {

    private GameObject scalePanel = null;
    private GameObject rotatePanel = null;

    private void Start() {

        scalePanel = GameObject.Find("ScalePanel");
        rotatePanel = GameObject.Find("RotatePanel");

    }

    // Called from Android -> UnityPlayerActivity -> onConfigurationChanged()
    private void OnRotateScreen(string orientation) {

        if (scalePanel != null && rotatePanel != null) {
            if (orientation == "LANDSCAPE") {
                LoadLandscapeLayout();
            } else if (orientation == "PORTRAIT") {
                LoadPortraitLayout();
            }
        }

    }

    private void LoadLandscapeLayout() {
        scalePanel.GetComponent<RectTransform>().anchorMin = new Vector2(0.05f, 0.3f);
        scalePanel.GetComponent<RectTransform>().anchorMax = new Vector2(0.1f, 0.7f);

        rotatePanel.GetComponent<RectTransform>().anchorMin = new Vector2(0.15f, 0.3f);
        rotatePanel.GetComponent<RectTransform>().anchorMax = new Vector2(0.2f, 0.7f);
    }

    private void LoadPortraitLayout() {
        scalePanel.GetComponent<RectTransform>().anchorMin = new Vector2(0.09f, 0.38f);
        scalePanel.GetComponent<RectTransform>().anchorMax = new Vector2(0.16f, 0.78f);

        rotatePanel.GetComponent<RectTransform>().anchorMin = new Vector2(0.2f, 0.38f);
        rotatePanel.GetComponent<RectTransform>().anchorMax = new Vector2(0.27f, 0.78f);
    }
}
