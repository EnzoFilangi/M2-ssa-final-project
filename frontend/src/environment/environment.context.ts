import { createContext } from 'react';

export interface Environment {
  apiUrl: string;
  cdnUrl?: string;
  buildInfo?: BuildInformation;
}

export interface BuildInformation {
  sha?: string;
  buildDate?: Date;
}

export const EnvironmentContext = createContext<Environment>({ apiUrl: '' });
