export interface Showroom {
    id?: number;
    name: string;
    commercialRegistrationNumber: string;
    managerName?: string;
    contactNumber: string;
    address?: string;
  }
  
  export interface ShowroomListItem {
    id: number;
    name: string;
    commercialRegistrationNumber: string;
    contactNumber: string;
  }