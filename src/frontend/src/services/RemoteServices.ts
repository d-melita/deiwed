import AttendeeDto from '@/models/deiwed/AttendeeDto';
import SessionDto from '@/models/deiwed/SessionDto';
import SessionAttendeesDto from '@/models/deiwed/SessionAttendeesDto';  
import DeiwedError from '@/models/error/DeiwedError';
import axios from 'axios';

const httpClient = axios.create();
httpClient.defaults.timeout = 50000;
httpClient.defaults.baseURL = process.env.VUE_APP_ROOT_API;
httpClient.defaults.headers.post['Content-Type'] = 'application/json';

export default class RemoteServices {
  static async getAttendees(): Promise<AttendeeDto[]> {
    return httpClient
      .get('/attendees')
      .then((response) => response.data)
      .catch(async (error) => {
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  static async getAttendee(id: number): Promise<AttendeeDto> {
    return httpClient
      .get('/attendees/' + id)
      .then((response) => response.data)
      .catch(async (error) => {
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  // get dishes from "https://eindhoven.rnl.tecnico.ulisboa.pt/food-store/api/v1/dishes"
  static getDishes(): Promise<[]> {
    return axios
      .get('https://eindhoven.rnl.tecnico.ulisboa.pt/food-store/api/v1/dishes')
      .then((response) => response.data)
      .catch(async (error) => {
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  static getSessionDishes(date: string): Promise<any[]> {
    return axios
      .get('https://eindhoven.rnl.tecnico.ulisboa.pt/food-store/api/v1/orders/' + date)
      .then((response) => [response.data])
      .catch(async (error) => {
        if (error.response.status === 404) {
          return [];
        }
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  static makeOrder(normalOptionDishId: number, vegetarianOptionDishId:number, date:string)
  {
    return axios
      .post('https://eindhoven.rnl.tecnico.ulisboa.pt/food-store/api/v1/orders/' + date, {
        "normalOptionDishId": normalOptionDishId,
        "vegetarianOptionDishId": vegetarianOptionDishId
      }, {headers: {'Content-Type': 'application/json', "Accept": "application/json", "Authorization": " Bearer ist199202"}}
      )
      .then((response) => response.data)
      .catch(async (error) => {
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  static async getSessions(): Promise<SessionDto[]> {
    return httpClient
      .get('/sessions')
      .then((response) => response.data)
      .catch(async (error) => {
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  static async getSession(id: number): Promise<SessionDto> {
    return httpClient
      .get('/sessions/' + id)
      .then((response) => response.data)
      .catch(async (error) => {
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  //add attendee to database
  static async addAttendee(attendee: AttendeeDto){
    return httpClient
      .post('/attendees', attendee)
      .then((response) => response.data)
      .catch(async (error) => {
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  static async editAttendee(attendee: AttendeeDto){
    return httpClient
      .put('/attendees/' + attendee.id, attendee)
      .then((response) => response.data)
      .catch(async (error) => {
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  static async removeAttendee(id: number){
    return httpClient
      .delete('/attendees/' + id)
      .then((response) => response.data)
      .catch(async (error) => {
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  static async editSession(session: SessionDto){
    return httpClient
      .put('/sessions/edit-session' + session.id, session)
      .then((response) => response.data)
      .catch(async (error) => {
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  static async removeSession(id: number){
    return httpClient
      .delete('/sessions/' + id)
      .then((response) => response.data)
      .catch(async (error) => {
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  static async addSession(session: SessionDto){
    return httpClient
      .post('/sessions', session)
      .then((response) => response.data)
      .catch(async (error) => {
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  static async getAllSessionAttendees(id: number): Promise<SessionAttendeesDto[]> {
    return httpClient
      .get('/sessions/' + id + '/attendees')
      .then((response) => response.data)
      .catch(async (error) => {
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  static async getAllSessionsOfAttendee(id: number): Promise<SessionAttendeesDto[]> {
    return httpClient
      .get('/attendee' + id + '/sessions')
      .then((response) => response.data)
      .catch(async (error) => {
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  static async addAttendeeToSession(sessionAttendee: SessionAttendeesDto){
    return httpClient
      .post('/sessions/' + sessionAttendee.sessionId + '/attendees/' + sessionAttendee.attendeeId, 
      sessionAttendee)
      .then((response) => response.data)
      .catch(async (error) => {
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  static async removeAttendeeFromSession(sessionId: number, attendeeId: number){
    return httpClient
      .delete('/sessions/' + sessionId + '/attendees/' + attendeeId)
      .then((response) => response.data)
      .catch(async (error) => {
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  static async errorMessage(error: any): Promise<string> {
    if (error.message === 'Network Error') {
      return 'Unable to connect to server';
    } else if (error.message.split(' ')[0] === 'timeout') {
      return 'Request timeout - Server took too long to respond';
    } else if (error.response?.data?.message) {
      return error.response.data.message;
    } else {
      return 'Unknown Error - Contact admin';
    }
  }
}
