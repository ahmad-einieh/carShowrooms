export interface Car {
  id?: number;
  vin: string;
  maker: string;
  model: string;
  modelYear: number;
  price: number;
  showroomId: number;
}

export interface CarListItem {
  id: number;
  vin: string;
  maker: string;
  model: string;
  modelYear: number;
  price: number;
  carShowroomName: string;
  contactNumber: string;
}