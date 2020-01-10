declare module 'react-native-default-preference' {
    export interface RNDefaultPreferenceKeys {
        [key: string]: string;
    }

    export default class RNDefaultPreference {
        static get(key: string): Promise<string>;
        static set(key: string, value: string): Promise<void>;
        static clear(key: string): Promise<void>;
        static getMultiple(keys: string[]): Promise<RNDefaultPreferenceKeys>;
        static setMultiple(data: RNDefaultPreferenceKeys): Promise<void>;
        static clearMultiple(keys: string[]): Promise<void>;
        static getAll(): Promise<RNDefaultPreferenceKeys>;
        static clearAll(): Promise<void>;

        static getName(): Promise<string>;
        static setName(name: string): Promise<void>;
    }
}