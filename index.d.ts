declare module 'react-native-default-preference' {
    export default class DefaultPreference {
        static get(key: string): Promise<string>;
        static set(key: string, value: string): Promise<void>;
        static clear(key: string): Promise<void>;
        static getMultiple(keys: Array<string>): Promise<Array<string>>;
        static setMultiple(data: Map<string, string>): Promise<void>;
        static clearMultiple(keys: Array<string>): Promise<void>;
        static getAll(): Promise<Map<string, string>>;
        static clearAll(): Promise<void>;

        /** Gets and sets the current preferences file name (android) or user default suite name (ios) **/
        static getName(): Promise<string>;
        static setName(name: string): Promise<void>;
    }
}