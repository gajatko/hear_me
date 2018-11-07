import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit {
  private question: TestQuestion;
  @ViewChild('audioOption') audioPlayerRef: ElementRef;

  constructor(private http: HttpClient) { }

  public config$: Promise<Map<String, String>> | null = null;
  public testName: String = 'intervals';
  private resolveConfig: Function|null = null;

  ngOnInit() {
    console.log('init');
    this.config$ = new Promise<Map<String, String>>(resolve => this.resolveConfig = resolve);
    this.loadConfig();
  }

  private loadConfig() {
    console.log('Loading default config...');
    this.http.get('http://localhost:8080/ear/get_config/' + this.testName)
      .subscribe((value: any) => {
        console.log('Gotcha config: ');
        console.log(value);
        this.resolveConfig(new Map<String, String>(Object.entries(value)));
        console.log('config$ content:');
        console.log(this.config$);
      });
  }

  public loadQuestion() {
    this.config$.then( config =>
      this.http.post('http://localhost:8080/ear/test/' + this.testName, config)
        .subscribe(value => {
          this.question = <TestQuestion>value;
          this.loadAudio();
        })
    );
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
