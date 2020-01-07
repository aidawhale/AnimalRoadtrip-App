using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class SabanaProp : MonoBehaviour {

    public enum Prop {
        Apple,
        Ball,
        Bucket,
        Flower1,
        Flower2,
        Flower3,
        Kite
    };

    public Prop prop;
    private GameObject image = null;
    private Vector3 position;
    private Color alpha;

    // Start is called before the first frame update
    void Start() {
        position = transform.position;
        alpha = new Color(255, 255, 255, 105);

        string imageName = "";
        switch (prop) {
            case Prop.Apple:
                imageName = "AppleImage";
                break;
            case Prop.Ball:
                imageName = "BallImage";
                break;
            case Prop.Bucket:
                imageName = "BucketImage";
                break;
            case Prop.Flower1:
                imageName = "FlowerImage1";
                break;
            case Prop.Flower2:
                imageName = "FlowerImage2";
                break;
            case Prop.Flower3:
                imageName = "FlowerImage3";
                break;
            case Prop.Kite:
                imageName = "KiteImage";
                break;
            default:
                imageName = "notFound";
                break;
        }
        image = GameObject.Find(imageName);

        MyStart();
    }

    public virtual void MyStart() { }

    public void OnResetPosition() {
        transform.position = position;
        image.GetComponent<Image>().color = alpha;
        MyOnResetPosition();
    }

    public virtual void MyOnResetPosition() { }

    public void OnTouchDetected() {
        if (image != null) {
            image.GetComponent<Image>().color = Color.white;
        }
    }
}
