import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';


@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit {
  private question: TestQuestion;
  @ViewChild('audioOption') audioPlayerRef: ElementRef;

  constructor(private http: HttpClient) { }

  config: any;
  testName: String = 'intervals';

  ngOnInit() {
    console.log('init');
    this.http.get('http://localhost:8080/ear/get_config/' + this.testName)
      .subscribe(value => this.config = value);
  }

  public loadQuestion() {
    this.http.post('http://localhost:8080/ear/test/' + this.testName,
      this.config)
      .subscribe(value => {
        this.question = <TestQuestion>value;
        this.loadAudio();
      });
  }

  public loadAudio() {
    if (this.question == null) {
      console.log('Question not loaded!');
    } else {
      this.http.post('http://localhost:8080/ear/audio',
        this.question.audioPattern, { responseType: 'arraybuffer' })
        .subscribe(wavBytes => this.playWave(wavBytes));
    }
  }
  private playWave(byteArray: ArrayBuffer) {
    const blob = new Blob([byteArray], { type: 'audio/wav' });
    const url = URL.createObjectURL(blob);
    const audio = document.getElementById('audioElement') as HTMLAudioElement;
    const source = document.getElementById('sourceElement') as HTMLSourceElement;
    source.src = url;
    audio.load();
    audio.play();
  }
}
