import AttendeeDto  from './AttendeeDto';
import  DishDto  from './DishDto';

export default class SessionDto {
    id: number = 0;
    date: string = '';
    theme: string = '';
    speaker: string = '';
    image: string = '';
    dishes: DishDto[] = [];
    attendees: AttendeeDto[] = [];
  }